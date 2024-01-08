#include <stm32f407xx.h>
#include "rcc_config.h"

void RCC_SYS_CLOCK (void)
{
		
	
	#define PLL_M 	8    //4
	#define PLL_N 	336 //180
	#define PLL_P 	0    // PLLP = 2

	RCC->CR |= RCC_CR_HSEON;  
	while (!(RCC->CR & RCC_CR_HSERDY));
	
	RCC->APB1ENR |= RCC_APB1ENR_PWREN;
	PWR->CR |= PWR_CR_VOS; 
	
	FLASH->ACR = FLASH_ACR_ICEN | FLASH_ACR_DCEN | FLASH_ACR_PRFTEN | FLASH_ACR_LATENCY_5WS;
	// AHB PR
	RCC->CFGR |= RCC_CFGR_HPRE_DIV1;
	
	// APB1 PR
	RCC->CFGR |= RCC_CFGR_PPRE1_DIV4;
	
	// APB2 PR
	RCC->CFGR |= RCC_CFGR_PPRE2_DIV2;
	
	RCC->PLLCFGR = (PLL_M <<0) | (PLL_N << 6) | (PLL_P <<16) | (RCC_PLLCFGR_PLLSRC_HSE);

	RCC->CR |= RCC_CR_PLLON;
	while (!(RCC->CR & RCC_CR_PLLRDY));
	
	RCC->CFGR |= RCC_CFGR_SW_PLL;
	while ((RCC->CFGR & RCC_CFGR_SWS) != RCC_CFGR_SWS_PLL);
}
