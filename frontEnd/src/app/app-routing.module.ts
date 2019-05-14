import { MovieDetailComponent } from './movie/movie-detail/movie-detail.component';
import { EditComponent } from './edit/edit.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  { path: 'filmes', loadChildren: './movie/movie.module#MovieModule'  },
  { path: 'series', loadChildren: './serie/serie.module#SerieModule' },
  { path:'editar/:id', component:EditComponent},
  {path:'pessoas', loadChildren:'./pessoa/pessoa.module#PessoaModule'},
  { path: '', pathMatch: 'full', redirectTo: 'filmes'},
  { path: '**', redirectTo: 'filmes' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
