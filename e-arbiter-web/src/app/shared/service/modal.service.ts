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

  public askQuestion(question: string, onApprove: any, onDeny?: any): void {
    const modalElement =
      $(`<div class="ui small basic modal">
            <div class="ui icon header" style="white-space: pre-line"><i class="help icon"></i>${question}</div>
            <div class="actions">
                <div class="ui red basic cancel inverted button">
                    <i class="remove icon"></i> Nie
                </div>
                <div class="ui green ok inverted button">
                    <i class="checkmark icon"></i> Tak
                </div>
            </div>
         </div>`);

    this.bodyElement.append(modalElement);
    modalElement
      .modal({
        closable: false,
        onApprove: onApprove,
        onDeny: () => (onDeny && onDeny()) || true
      })
      .modal('show');
  }

}
