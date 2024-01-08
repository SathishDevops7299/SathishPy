#include <stm32f407xx.h>
#include "delay.h"

void TIMER_SIX_INIT (void)
{
	
	RCC->APB1ENR |= (1<<4);  
	

	TIM6->PSC = 84 - 1;  
	TIM6->ARR = 0xffff;  

	TIM6->CR1 |= (1<<0);
	while (!(TIM6->SR & (1<<0))); 
}

void Delay_us (uint32_t us)
{
	TIM6->CNT = 0;
	while (TIM6->CNT < us);
}

void Delay_ms (uint32_t ms)
{
	uint32_t i=0;
	for (i=0; i<ms; i++)
	{
		Delay_us (1000); 
	}
}
