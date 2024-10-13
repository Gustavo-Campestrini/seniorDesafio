import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { HospedeService } from '../../service/hospede.service';
import { CheckIn } from '../../models/hospede.CheckIn';
import { Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-consult',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './consult.component.html',
  styleUrls: ['./consult.component.scss'],
})
export class ConsultComponent implements OnInit {
  data: CheckIn[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalItems: number = 0;
  pessoasPresentes: boolean = false;
  pessoasSairam: boolean = false;
  
  private subscription!: Subscription;
  
  constructor(private hospedeService: HospedeService) {}

  ngOnInit(): void {
    this.subscription = this.hospedeService
      .getCheckIns()
      .subscribe((checkIns) => {
        this.data = checkIns;
        this.totalItems = this.data.length;
      });
  }

  get paginatedData(): CheckIn[] {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;

    let filteredData = this.data;

    if (this.pessoasPresentes) {
      filteredData = filteredData.filter(
        (checkIn) =>
          !checkIn.dataSaida || new Date(checkIn.dataSaida) > new Date()
      );
    }

    if (this.pessoasSairam) {
      filteredData = filteredData.filter(
        (checkIn) =>
          checkIn.dataSaida && new Date(checkIn.dataSaida) <= new Date()
      );
    }

    const currentData = filteredData.slice(
      startIndex,
      startIndex + this.itemsPerPage
    );

    return currentData.map((checkIn) => {
      checkIn.valor = this.calcularValorTotal(checkIn);
      return checkIn;
    });
  }

  setFilter(option: string) {
    if (option === 'present') {
      this.pessoasPresentes = true;
      this.pessoasSairam = false;
    } else if (option === 'left') {
      this.pessoasPresentes = false;
      this.pessoasSairam = true;
    }
  }

  goToPage(page: number) {
    this.currentPage = page;
  }

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  calcularValorTotal(checkIn: CheckIn): number {
    let total = 0;
    const dataEntrada = new Date(checkIn.dataEntrada);
    const dataSaida = new Date(checkIn.dataSaida);

    let currentDate = new Date(dataEntrada);

    while (currentDate <= dataSaida) {
      const dayOfWeek = currentDate.getDay();

      let valorDiaria = dayOfWeek === 0 || dayOfWeek === 6 ? 150 : 120;

      if (checkIn.veiculo) {
        valorDiaria += dayOfWeek === 0 || dayOfWeek === 6 ? 20 : 15;
      }

      total += valorDiaria;

      currentDate.setDate(currentDate.getDate() + 1);
    }

    const horaSaida = dataSaida.getHours();
    const minutosSaida = dataSaida.getMinutes();
    if (horaSaida > 16 || (horaSaida === 16 && minutosSaida > 30)) {
      total += dataSaida.getDay() === 0 || dataSaida.getDay() === 6 ? 150 : 120;
    }

    return total;
  }
}
