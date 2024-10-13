import { Component } from '@angular/core';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HospedeService } from '../../service/hospede.service';
import { Hospede } from '../../models/hospede.model';
import { v4 as uuidv4 } from 'uuid';

@Component({
  selector: 'app-new-person',
  standalone: true,
  imports: [NgxMaskDirective, NgxMaskPipe, ReactiveFormsModule],
  templateUrl: './new-person.component.html',
  styleUrl: './new-person.component.scss',
})
export class NewPersonComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private hospedeService: HospedeService) {
    this.form = this.fb.group({
      nome: ['', Validators.required],
      documento: ['', Validators.required],
      telefone: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      const novoHospede: Hospede = {
        id: uuidv4(),
        nome: this.form.value.nome,
        documento: this.form.value.documento,
        telefone: this.form.value.telefone,
      };

      this.hospedeService.adicionarHospede(novoHospede);

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
