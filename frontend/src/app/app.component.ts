import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './component/header/header.component';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { NewPersonComponent } from './component/new-person/new-person.component';
import { ConsultComponent } from './component/consult/consult.component';
import { NewCheckInComponent } from './component/checkIn/checkIn.component';
import { Hospede } from './models/hospede.model';
import { HospedeService } from './service/hospede.service';
import { CheckIn } from './models/hospede.CheckIn';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    NgxMaskDirective,
    NewPersonComponent,
    ConsultComponent,
    NewCheckInComponent,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  providers: [provideNgxMask()],
})
export class AppComponent {
  title = 'desafio_hotel_senior';

  constructor(private hospedeService: HospedeService) {}

  adicionarHospede(novoHospede: Hospede) {
    this.hospedeService.adicionarHospede(novoHospede);
  }

  registrarCheckIn(checkIn: CheckIn) {
    this.hospedeService.registrarCheckIn(checkIn);
  }

  get hospedes() {
    return this.hospedeService.getHospedes();
  }

  get checkIns() {
    return this.hospedeService.getCheckIns();
  }
}
