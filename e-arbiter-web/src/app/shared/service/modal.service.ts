import {Injectable} from '@angular/core';

declare var $: any;

@Injectable()
export class ModalService {

  private bodyElement = $('body');

  // TODO: add second parameter: a callback function to execute on alert close
  // for example (redirect to another page)
  public showAlert(message: string): void {
    const modalElement =
      $(`<div class="ui small basic modal">
            <div class="ui icon header" style="white-space: pre-line"><i class="help icon"></i>${message}</div>
         </div>`);

    this.bodyElement.append(modalElement);
    modalElement
      .modal({
        onHidden: () => modalElement.remove()
      })
      .modal('show');
  }

}
