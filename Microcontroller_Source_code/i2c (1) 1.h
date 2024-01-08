
#include <stm32f407xx.h>
#include <stdint.h>

void I2C_Config(void);
void I2C_Start(void);
void I2C_write(uint8_t data);
void I2C_Address(uint8_t address);
void I2C_WriteMulti(uint8_t *data, uint16_t size);
void I2C_Read(uint8_t Address, uint8_t *buffer, uint8_t size);
void I2C_stop(void);

