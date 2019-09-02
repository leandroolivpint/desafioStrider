import { Component, SimpleChanges } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '../../node_modules/@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { isGeneratedFile } from '@angular/compiler/src/aot/util';

// tslint:disable-next-line: component-class-suffix
export class Tarefa {
  descricao: string;
  flag: boolean;
  img64: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})



export class AppComponent {
  panelOpenState = false;
  title = 'desafioStrider';
  tarefas: any[];
  tarefasPendentes = [];
  tarefasFeito = [];
  formTask: FormGroup;
  constructor(private http: HttpClient, private fb: FormBuilder, private _sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.criarForm();
    this.carregarTarefas();
    setInterval(async() => { await this.carregarTarefas(); }, 10000);
  }
  abrirImagem($event) {
    if (!$event.target.children[0]) {
      return;
    }
    if ($event.target.style.height == '100px') {
      $event.target.children[0].style.display = 'none';
      $event.target.style.height = '10px';
    } else {
      console.log($event.target.children);
      $event.target.children[0].style.display = 'block';
      $event.target.style.height = '100px';
    }
  }

  async onSubmit() {
    // fonte: https://www.techiediaries.com/angular-by-example-httpclient-get/
    console.log(this.formTask.value.descricao);
    if (this.formTask != null) {
      const response = await this.http.post('http://localhost:3333/t', {
        descricao: this.formTask.value.descricao
      }).subscribe((res) => {
          console.log(res);
          this.carregarTarefas();
        });
    }
  }

  async carregarTarefas() {
    const response = await this.http.get<any[]>('http://localhost:3333/t').subscribe((res) => {
        this.tarefas = res;
        this.tarefas.map(tar => {
          if (tar.img64 !== null) {
            tar.imgPath = this._sanitizer.bypassSecurityTrustResourceUrl('data:image/jpeg;base64,' + tar.img64);
          } else {
            tar.imgPath = '';
          }
        });
        this.carregarTarefasPendentes();
        this.carregarTarefasFeitas();
        // console.log(res);
      });
  }

  criarForm() {
    this.formTask = this.fb.group({
      descricao: ['']
    });
  }


  carregarTarefasPendentes() {
    if (this.tarefas) {
      this.tarefasPendentes = this.tarefas.filter(tar => tar.flag == false);
      console.log(this.tarefasPendentes);
    }
  }

  carregarTarefasFeitas() {
    if (this.tarefas) {
      this.tarefasFeito = this.tarefas.filter(tar => tar.flag == true);
    }
  }

}
