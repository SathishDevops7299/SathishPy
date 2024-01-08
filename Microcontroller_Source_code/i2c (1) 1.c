#include <stm32f407xx.h>

#include "i2c.h"
#define BUFFER_SIZE 10
static uint8_t receivedData[BUFFER_SIZE];

void I2C_Config(void);
void I2C_Start(void);
void I2C_stop(void);
void I2C_write(uint8_t data);
void I2C_Address(uint8_t address);
void I2C_WriteMulti(uint8_t *data, uint16_t size);
void I2C_Read(uint8_t Address, uint8_t *buffer, uint8_t size);
	
extern void rcc_setup(void);

void I2C_Config(void)
{
	RCC->APB1ENR |= (1<<21);  /* enable I2C CLOCK*/
	RCC->AHB1ENR |= (1<<1);  /* Enable GPIOB CLOCK*/
	GPIOB->MODER |= (2<<16) | (2<<18);  /* Bits (17:16)= 1:0 --> Alternate Function for Pin PB8; Bits (19:18)= 1:0 --> Alternate Function for Pin PB9*/
	GPIOB->OTYPER |= (1<<8) | (1<<9);  /*  Bit8=1, Bit9=1  output open drain */
	GPIOB->OSPEEDR |= (3<<16) | (3<<18);  /* Bits (17:16)= 1:1 --> High Speed for PIN PB8; Bits (19:18)= 1:1 --> High Speed for PIN PB9 */
	GPIOB->PUPDR |= (1<<16) | (1<<18);  /* Bits (17:16)= 0:1 --> Pull up for PIN PB8; Bits (19:18)= 0:1 --> pull up for PIN PB9*/
	GPIOB->AFR[1] |= (4<<0) | (4<<4);  /* Bits (3:2:1:0) = 0:1:0:0 --> AF4 for pin PB8;  Bits (7:6:5:4) = 0:1:0:0 --> AF4 for pin PB9*/

	I2C1->CR1 |= (1<<15);  /* reset the I2C*/
	I2C1->CR1 &= (0<<15);  /* Normal operation*/
	
	/* Program the peripheral input clock in I2C_CR2 Register in order to generate correct timings*/
	I2C1->CR2 |= (45<<0);  /* PCLK1 FREQUENCY in MHz  */  
	
	/* Configure the clock control registers*/
	I2C1->CCR = 225<<0; 
	
	/* Configure the rise time register*/
	I2C1->TRISE = 46;  
	
	/* Program the I2C_CR1 register to enable the peripheral*/
	I2C1->CR1 |= (1<<0);  /* Enable I2C*/
	
}

void I2C_Start(void)
{
	I2C1->CR1 |= (1<<10);  /* Enable the ACK*/
	I2C1->CR1 |= (1<<8);  /* Generate START*/
//	while(!(I2C1->SR1&(1<<0))); /*Wait for SB bit to set*/
}

void I2C_write(uint8_t data)
{
//	while (!(I2C1->SR1 & (1<<7)));  /* wait for TXE bit to set*/
	I2C1->DR = data;
//	while (!(I2C1->SR1 & (1<<2)));  /* wait for BTF bit to set*/
}

void I2C_Address(uint8_t address)
{
	uint16_t temp=0;
	
	I2C1->DR = address;  /*  send the address*/
//	while (!(I2C1->SR1 & (1<<1)));  /* wait for ADDR bit to set*/
	/*temp = I2C1->SR1 | I2C1->SR2;  *//* read SR1 and SR2 to clear the ADDR bit*/
	temp |= I2C1->SR1;
}
	
void I2C_WriteMulti(uint8_t *data, uint16_t size)
{
	/*while (!(I2C1->SR1 & (1<<7)));*/
	while(size)
	{
		/*while(!(while (!(I2C1->SR1 & (1<<7)));*/
		I2C1->DR=(uint8_t)*data++; /*send data*/
		size--;
	}
	/*while (!(I2C1->SR1 & (1<<2)));*/
}

void I2C_Read(uint8_t Address, uint8_t *buffer, uint8_t size)
{
	int remaining = size;
	uint16_t temp=0;

	if (size == 1)
	{
		I2C1->DR = Address;  //  send the address
	/*	while (!(I2C1->SR1 & (1<<1))); */ // wait for ADDR bit to set
		I2C1->CR1 &= (0<<10);  // clear the ACK bit 
		/*uint8_t temp = I2C1->SR1 | I2C1->SR2;  */
			temp |= I2C1->SR1;// read SR1 and SR2 to clear the ADDR bit.... EV6 condition
		I2C1->CR1 |= (1<<9);  // Stop I2C
	/*	while (!(I2C1->SR1 & (1<<6)));  // wait for RxNE to set*/
		buffer[size-remaining] = I2C1->DR;  // Read the data from the DATA REGISTER
	}
	else 
	{
		I2C1->DR = Address;  //  send the address
		/*while (!(I2C1->SR1 & (1<<1)));*/  // wait for ADDR bit to set
		/*uint8_t temp = I2C1->SR1 | I2C1->SR2;*/  // read SR1 and SR2 to clear the ADDR bit
		temp |= I2C1->SR1;
		while (remaining>2)
		{
			/*while (!(I2C1->SR1 & (1<<6))); */ // wait for RxNE to set
			buffer[size-remaining] = I2C1->DR;  // copy the data into the buffer			
			I2C1->CR1 |= 1<<10;  // Set the ACK bit to Acknowledge the data received
			remaining--;
		}
		// Read the SECOND LAST BYTE
		/*while (!(I2C1->SR1 & (1<<6)));  */// wait for RxNE to set
		buffer[size-remaining] = I2C1->DR;
		I2C1->CR1 &= (0<<10);  // clear the ACK bit 
		I2C1->CR1 |= (1<<9);  // Stop I2C
		remaining--;
		// Read the Last BYTE
		
		buffer[size-remaining] = I2C1->DR;  // copy the data into the buffer
//		while (!(I2C1->SR1 & (1<<6)));  // wait for RxNE to set
	}	
}
void I2C_stop(void)
{
	I2C1->CR1 |= (1<<9);
}