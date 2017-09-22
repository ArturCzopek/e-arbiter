import {Injectable} from '@angular/core';
import {TmpTournamentDetails, TournamentDetails} from '../interface/tournament-details.interface';
import {Observable} from 'rxjs/Observable';
import {TmpAccessDetails} from '../interface/access-details.interface';
import {TournamentStatus} from '../interface/tournament-status.enum';
import {TmpLocalDateTime} from '../interface/local-date-time.interface';
import {TmpTaskPreview} from '../interface/task-preview.interface';

@Injectable()
export class TournamentDetailsService {

  public getDetailsForTournament(id: string): Observable<TournamentDetails> {
    // TODO #84 - add call to db and remove this mock request
    // also, remove tmp classes
    console.log('Tournament id', id);

    const random = Math.random();
    // for now, info about results visibility doesn't matter, will be implemented in #86
    // there are mocks only for tournaments if we participate in tournament and its status
    if (random < 0.25) { // public tournament, user participates, active
      return Observable.create(observer => {
        setTimeout(() => observer.next(
          new TmpTournamentDetails(
            id,
            'TestowyUser',
            'Testowy publiczny turniej, uczestnicze, aktywny',
            new TmpAccessDetails(true, false, true, true),
            TournamentStatus.ACTIVE,
            'Culpa dolore incididunt ullamco mollit. Ad culpa officia ad exercitation esse culpa velit mollit occaecat ' +
            'magna consectetur laboris esse. Non excepteur non enim irure exercitation duis.',
            36,
            new TmpLocalDateTime(0, 0, 15, 20, 8, 2017),
            new TmpLocalDateTime(0, 0, 15, 20, 10, 2017),
            [
              new TmpTaskPreview(
                'Pierwsze proste programistyczne zadanie',
                'Zrob mi tu jakiegos pitagorasa, albo jakas siatke. Jak tego nie zrobisz to jestes lamus',
                5,
                3.5,
                3
              ),
              new TmpTaskPreview(
                'Kolejne koderskie',
                'Brak opisu prawie, wiesz co masz robic',
                1,
                1,
                1
              ),
              new TmpTaskPreview(
                'Quiz dla analfabety',
                'Co wiesz o spółgłoskach? Sprawdźmy',
                5,
                0,
                1,
                1
              ),
              new TmpTaskPreview(
                'Samogłoski',
                'reprehenderit fugiat ad deserunt dolore esse est sunt consequat cillum aliquip quis Lorem aute quis sint incididunt ullamco eiusmod pariatur',
                4,
                3.5,
                1,
                1
              ),
            ],
            15,
            8
          )
        ), 500);

        setTimeout(() => observer.complete(), 650);
      });
    } else if (random < 0.5) { // public tournament, user participates, finished
      return Observable.create(observer => {
        setTimeout(() => observer.next(
          new TmpTournamentDetails(
            id,
            'TestowyUser',
            'Testowy publiczny turniej, uczestnicze, zakonczony',
            new TmpAccessDetails(true, false, true, true),
            TournamentStatus.FINISHED,
            'Culpa dolore incididunt ullamco mollit. Ad culpa officia ad exercitation esse culpa velit mollit occaecat ' +
            'magna consectetur laboris esse. Non excepteur non enim irure exercitation duis.',
            36,
            new TmpLocalDateTime(0, 0, 15, 20, 8, 2017),
            new TmpLocalDateTime(0, 0, 15, 20, 9, 2017),
            [
              new TmpTaskPreview(
                'Pierwsze proste programistyczne zadanie',
                'Zrob mi tu jakiegos pitagorasa, albo jakas siatke. Jak tego nie zrobisz to jestes lamus',
                5,
                3.5,
                3
              ),
              new TmpTaskPreview(
                'Kolejne koderskie',
                'Brak opisu prawie, wiesz co masz robic',
                1,
                1,
                1
              ),
              new TmpTaskPreview(
                'Quiz dla analfabety',
                'Co wiesz o spółgłoskach? Sprawdźmy',
                5,
                0,
                1,
                1
              ),
              new TmpTaskPreview(
                'Samogłoski',
                'reprehenderit fugiat ad deserunt dolore esse est sunt consequat cillum aliquip quis Lorem aute quis sint incididunt ullamco eiusmod pariatur',
                4,
                3.33,
                1,
                1
              ),
            ],
            15,
            8
          )
        ), 500);

        setTimeout(() => observer.complete(), 650);
      });
    } else if (random < 0.75) { // private tournament, user participates, active
      return Observable.create(observer => {
        setTimeout(() => observer.next(
          new TmpTournamentDetails(
            id,
            'TestowyUser',
            'Testowy prywatny turniej aktywny, uczestnicze',
            new TmpAccessDetails(false, false, true, true),
            TournamentStatus.ACTIVE,
            'Culpa dolore incididunt ullamco mollit. Ad culpa officia ad exercitation esse culpa velit mollit occaecat ' +
            'magna consectetur laboris esse. Non excepteur non enim irure exercitation duis.',
            36,
            new TmpLocalDateTime(0, 0, 15, 20, 8, 2017),
            new TmpLocalDateTime(0, 0, 15, 20, 10, 2017),
            [
              new TmpTaskPreview(
                'Pierwsze proste programistyczne zadanie',
                'Zrob mi tu jakiegos pitagorasa, albo jakas siatke. Jak tego nie zrobisz to jestes lamus',
                5,
                3.5,
                3
              ),
              new TmpTaskPreview(
                'Kolejne koderskie',
                'Brak opisu prawie, wiesz co masz robic',
                1,
                1,
                1
              ),
              new TmpTaskPreview(
                'Quiz dla analfabety',
                'Co wiesz o spółgłoskach? Sprawdźmy',
                5,
                0,
                1,
                1
              ),
              new TmpTaskPreview(
                'Samogłoski',
                'reprehenderit fugiat ad deserunt dolore esse est sunt consequat cillum aliquip quis Lorem aute quis sint incididunt ullamco eiusmod pariatur',
                4,
                3.5,
                1,
                1
              ),
            ],
            15,
            8
          )
        ), 500);

        setTimeout(() => observer.complete(), 650);
      });
    } else { // private tournament, user participates, finished
      return Observable.create(observer => {
        setTimeout(() => observer.next(
          new TmpTournamentDetails(
            id,
            'TestowyUser',
            'Testowy prywatny turniej, uczestnicze, zakonczony',
            new TmpAccessDetails(false, false, true, true),
            TournamentStatus.FINISHED,
            'Culpa dolore incididunt ullamco mollit. Ad culpa officia ad exercitation esse culpa velit mollit occaecat ' +
            'magna consectetur laboris esse. Non excepteur non enim irure exercitation duis.',
            36,
            new TmpLocalDateTime(0, 0, 15, 20, 8, 2017),
            new TmpLocalDateTime(0, 0, 15, 20, 9, 2017),
            [
              new TmpTaskPreview(
                'Pierwsze proste programistyczne zadanie',
                'Zrob mi tu jakiegos pitagorasa, albo jakas siatke. Jak tego nie zrobisz to jestes lamus',
                5,
                3.5,
                3
              ),
              new TmpTaskPreview(
                'Kolejne koderskie',
                'Brak opisu prawie, wiesz co masz robic',
                1,
                1,
                1
              ),
              new TmpTaskPreview(
                'Quiz dla analfabety',
                'Co wiesz o spółgłoskach? Sprawdźmy',
                5,
                0,
                1,
                1
              ),
              new TmpTaskPreview(
                'Samogłoski',
                'reprehenderit fugiat ad deserunt dolore esse est sunt consequat cillum aliquip quis Lorem aute quis sint incididunt ullamco eiusmod pariatur',
                4,
                3.33,
                1,
                1
              ),
            ],
            15,
            8
          )
        ), 500);

        setTimeout(() => observer.complete(), 650);
      });
    }
  }
}
