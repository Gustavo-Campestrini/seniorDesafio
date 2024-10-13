import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Hospede } from '../models/hospede.model';
import { CheckIn } from '../models/hospede.CheckIn';
import { v4 as uuidv4 } from 'uuid';

@Injectable({
  providedIn: 'root',
})
export class HospedeService {
  private checkInsSubject = new BehaviorSubject<CheckIn[]>([]);

  hospedes: Hospede[] = [
    {
      id: uuidv4(),
      nome: 'Pedro Campos',
      documento: '123456',
      telefone: '99123 4567',
    },
    {
      id: uuidv4(),
      nome: 'Jorge Carvalho',
      documento: '654321',
      telefone: '98888 8888',
    },
  ];

  constructor() {
    const initialCheckIns: CheckIn[] = [
      {
        id: uuidv4(),
        pessoa: this.hospedes[0],
        dataEntrada: new Date('2024-10-10T08:00:00'),
        dataSaida: new Date('2024-10-12T10:00:00'),
        veiculo: true,
      },
      {
        id: uuidv4(),
        pessoa: this.hospedes[1],
        dataEntrada: new Date('2024-10-05T08:00:00'),
        dataSaida: new Date('2024-10-10T10:00:00'),
        veiculo: false,
      },
    ];

    this.checkInsSubject.next(initialCheckIns);
  }

  getCheckIns() {
    return this.checkInsSubject.asObservable();
  }

  getHospedes() {
    return this.hospedes;
  }

  adicionarHospede(hospede: Hospede) {
    const currentHospedes = this.hospedes.push(hospede);
  }

  registrarCheckIn(checkIn: CheckIn) {
    const currentCheckIns = this.checkInsSubject.getValue();
    this.checkInsSubject.next([...currentCheckIns, checkIn]);
  }
}
