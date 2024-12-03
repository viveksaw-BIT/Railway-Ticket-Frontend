// // angular import
// import { Component } from '@angular/core';

// // project import
// import { SharedModule } from 'src/app/theme/shared/shared.module';

// @Component({
//   selector: 'app-search-train',
//   standalone: true,
//   imports: [SharedModule],
//   templateUrl: './search-train.component.html',
//   styleUrls: ['./search-train.component.scss']
// })
// export default class SearchTrainComponent {}


// angular import
import { Component, OnInit } from '@angular/core';
import { RouterModule,Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SharedModule } from '../../../theme/shared/shared.module';
import { DataService } from 'src/app/service/data.service';
import { TrainDetailsComponent } from '../../default/dashboard/train-details/train-details.component';
import { BookedTicketDetailsComponent } from '../booked-ticket-details/booked-ticket-details.component';


@Component({
  selector: 'app-search-train',
  standalone: true,
  imports: [RouterModule,SharedModule,TrainDetailsComponent,BookedTicketDetailsComponent],
  templateUrl: './booking-details.component.html',
  styleUrls: ['./booking-details.component.scss']
})
export default class BookingDetailsComponent implements OnInit{

  endpoint: string = '';
  sourceStation: string = '';
  destinationStation: string = '';
  // sourceStation: number;
  // destinationStation: number;
  //stationOptions: string[] = [];
  stationOptions: any;
  date: string = '';
  currentDate: Date = new Date;
  //minDate: string = (new Date).toISOString().split('T')[0]; // Get 'YYYY-MM-DD'
  firstName: string = '';
  lastName: string = '';
  userName: string = '';
  password: string = '';
  userType: number = 1; // Using 1 for regular users and 0 for admin - only allowing new regular user registration

  errorMessage: string = '';
  successMessage: string = '';
  validationMessage: string = '';

  constructor(private dataService: DataService, private http: HttpClient,private router: Router) {}

  ngOnInit(): void {
    const endpoint = 'http://localhost:8083/stations';
    //http://localhost:8084/search/train/source/1/destination/3/date/2024-08-02
    //http://localhost:8084/search/trains/source/Station 1A/destination/Station 1C/date/2024-08-01
    this.dataService.fetchData(endpoint).subscribe(
      (response) => {
        this.stationOptions = response; // Assign the response to the data variable
        //this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }


  onSubmit() {

    const endpoint = 'http://localhost:8083/stations';

    // const registerData = {
    //   user_fname: this.firstName,
    //   user_lname: this.lastName,
    //   username: this.userName,
    //   password: this.password,
    //   user_type: this.userType
    // };
    if(Number(this.sourceStation)>=Number(this.destinationStation)){
      if(Number(this.sourceStation)>Number(this.destinationStation))
        this.validationMessage = 'No train available for the selected stations';
      else
      this.validationMessage = 'Source and Destination stations cannot be same';
    }
    else{
       this.validationMessage = '';
      this.endpoint=`http://localhost:8084/search/train/source/${this.sourceStation}/destination/${this.destinationStation}/date/${this.date}`;
      //this.validationMessage = this.endpoint;

    }
    //this.http.post('http://localhost:8082/login', loginData).subscribe(
    //   this.dataService.postData(endpoint,registerData).subscribe(
    //   (response) => {
    //     //console.log('Login successful:', response);
    //     // Handle successful login response
    //     if (response) {
    //       console.log('Registration successful:', response);
    //       this.successMessage = 'Registration successful. Redirecting to Login ...';
    //       // Handle successful login response
    //       // Redirect the user to a new URL
    //       // Storing the user data in local storage
    //       localStorage.setItem('user', JSON.stringify(response));
    //       setTimeout(() => {
    //         this.router.navigate(['/login']); // redirecting
    //       },2000);
    //     } else {
    //       // Handle case where response is null (wrong username or password)
    //       this.errorMessage = 'Could not create account. Please try again.';
    //     }

    //   },
    //   (error) => {
    //     console.error('Login error:', error);
    //     // Handle login error
    //     this.errorMessage = 'Could not connect to server. Please try again later.';
    //   }
    // );
  }

}

