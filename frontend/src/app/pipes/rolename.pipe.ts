import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'rolename'
})
export class RolenamePipe implements PipeTransform {

  transform(value: string, ...args: unknown[]): unknown {
    return value.substring(5);
  }

}
