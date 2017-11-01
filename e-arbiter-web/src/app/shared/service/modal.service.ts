import {Injectable} from '@angular/core';
import * as _ from 'lodash';

declare var $: any;

@Injectable()
export class ModalService {

  private bodyElement = $('body');

  public showAlert(message: string, onHiddenCallback?: any): void {
    const modalElement =
      $(`<div class="ui small basic modal">
            <div class="ui icon header" style="white-space: pre-line"><i class="help icon"></i>${message}</div>
         </div>`);

    this.bodyElement.append(modalElement);
    modalElement
      .modal({
        onHidden: () => {
          if (onHiddenCallback && _.isFunction(onHiddenCallback)) {
            onHiddenCallback();
          }
          modalElement.remove();
        }
      })
      .modal('show');
  }

}
