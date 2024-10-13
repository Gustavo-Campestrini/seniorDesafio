import { Hospede } from './hospede.model';

export interface CheckIn {
  id: string;
  pessoa: Hospede;
  dataEntrada: Date;
  dataSaida: Date;
  veiculo: boolean;
  valor?: number;
}
