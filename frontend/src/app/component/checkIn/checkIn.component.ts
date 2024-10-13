import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { Hospede } from '../../models/hospede.model';
import { HospedeService } from '../../service/hospede.service';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { v4 as uuidv4 } from 'uuid';
import { CheckIn } from '../../models/hospede.CheckIn';

@Component({
  selector: 'app-checkIn',
  standalone: true,
  imports: [NgxMaskDirective, NgxMaskPipe, CommonModule, ReactiveFormsModule],
  templateUrl: './checkIn.component.html',
  styleUrls: ['./checkIn.component.scss'],
})
export class NewCheckInComponent {
  hospedes: Hospede[] = [];
  form: FormGroup;

  constructor(private fb: FormBuilder, private hospedeService: HospedeService) {
    this.hospedes = this.hospedeService.getHospedes();

    this.form = this.fb.group({
      dataEntrada: [null, Validators.required],
      dataSaida: [null, Validators.required],
      pessoa: [null, Validators.required],
      veiculo: [false],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const checkIn: CheckIn = {
        id: uuidv4(),
        pessoa: this.form.value.pessoa,
        dataEntrada: new Date(this.form.value.dataEntrada),
        dataSaida: new Date(this.form.value.dataSaida),
        veiculo: this.form.value.veiculo,
      };

      this.hospedeService.registrarCheckIn(checkIn);

      this.showNotification('Salvo com sucesso!', 'success');

      this.form.reset();
    } else {
      this.showNotification(
        'Por favor, preencha todos os campos obrigatórios.',
        'error'
      );
    }
  }

  showNotification(message: string, type: string) {
    const notification = document.getElementById('notification');
    if (notification) {
      notification.textContent = message;

      let color;
      if (type == 'success') {
        color = '#4caf50';
      } else {
        color = 'red';
      }

      notification.style.backgroundColor = color;

      notification.classList.add('show');

      setTimeout(() => {
        notification.classList.remove('show');
      }, 3000);
    }
  }
}
