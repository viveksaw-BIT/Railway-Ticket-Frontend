// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-home',
//   standalone: true,
//   imports: [],
//   templateUrl: './home.component.html',
//   styleUrl: './home.component.scss'
// })
// export class HomeComponent {

// }

// angular import
// import { Component } from '@angular/core';

import { Component, OnInit } from '@angular/core';
import { RouterModule,Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SharedModule } from '../../../theme/shared/shared.module';
import { DataService } from 'src/app/service/data.service';
import { TrainDetailsComponent } from '../../default/dashboard/train-details/train-details.component';

// project import
// import { SharedModule } from 'src/app/theme/shared/shared.module';
// import { TrainDetailsComponent } from '../../default/dashboard/train-details/train-details.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule,SharedModule,TrainDetailsComponent],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export default class HomeComponent {
  active=1;

}
