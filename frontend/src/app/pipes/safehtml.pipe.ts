import { Pipe, PipeTransform } from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";

@Pipe({
  name: 'safehtml'
})
export class SafehtmlPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {
  }

  transform(value: unknown, ...args: unknown[]): unknown {
    return this.sanitizer.bypassSecurityTrustHtml(<string>value);
  }

}
