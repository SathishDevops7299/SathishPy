import { Pipe, PipeTransform } from '@angular/core';
  
@Pipe({
  name: 'nullAsApplyNow'
})
export class NullWithDefaultPipe implements PipeTransform {
  
  transform(value: any, defaultText: string = 'Apply Now'): any {
    if (typeof value === 'undefined' || value === null) {
      return defaultText;
    }
  
    return value;
  }
  
}