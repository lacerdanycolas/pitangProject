
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PessoaComponent } from './pessoa.component';
import { PessoaDetailComponent } from './pessoa-detail/pessoa-detail.component';


const routes: Routes = [
    {  path: '', component: PessoaComponent },
    {  path: 'detalhes/:id', component: PessoaDetailComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PessoaRoutingModule { }