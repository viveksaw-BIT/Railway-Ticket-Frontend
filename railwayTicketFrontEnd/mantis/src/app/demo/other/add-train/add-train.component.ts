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

@Component({
  selector: 'app-add-train',
  standalone: true,
  imports: [RouterModule,SharedModule,TrainDetailsComponent],
  templateUrl: './add-train.component.html',
  styleUrls: ['./add-train.component.scss']
})
export default class AddTrainComponent implements OnInit{

  endpoint: string = '';
  // sourceStation: string = '';
  // destinationStation: string = '';
  departureTime: string = '';
  arrivalTime: string = '';
  trainName: string = '';
  trainNumber: string = '';
  totalSeats: string = '';
  trainRoute: string = '';
  
  // sourceStation: number;
  // destinationStation: number;
  //stationOptions: string[] = [];
  trainRoutes: any;
  //stationOptions: any;
  date: string = '';
  currentDate: Date = new Date;
  //minDate: string = (new Date).toISOString().split('T')[0]; // Get 'YYYY-MM-DD'
  // firstName: string = '';
  // lastName: string = '';
  // userName: string = '';
  // password: string = '';
  // userType: number = 1; // Using 1 for regular users and 0 for admin - only allowing new regular user registration

  errorMessage: string = '';
  successMessage: string = '';
  validationMessage: string = '';

  constructor(private dataService: DataService, private http: HttpClient,private router: Router) {}

  ngOnInit(): void {
    const endpoint = 'http://localhost:8083/routes';
    //http://localhost:8084/search/train/source/1/destination/3/date/2024-08-02
    //http://localhost:8084/search/trains/source/Station 1A/destination/Station 1C/date/2024-08-01
    this.dataService.fetchData(endpoint).subscribe(
      (response) => {
        this.trainRoutes = response; // Assign the response to the data variable
        //this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  onSubmit() {

    const endpoint = 'http://localhost:8083/trains';

    const trainData = {
        name: this.trainName,
        number: this.trainNumber,
        routeId: this.trainRoute,
        scheduleDate: this.date,
        departureTime: this.departureTime,
        arrivalTime: this.arrivalTime,
        totalSeats: this.totalSeats
    };
    // if(Number(this.sourceStation)>=Number(this.destinationStation)){
    //   if(Number(this.sourceStation)>Number(this.destinationStation))
    //     this.validationMessage = 'No train available for the selected stations';
    //   else
    //   this.validationMessage = 'Source and Destination stations cannot be same';
    // }
    // else{
    //    this.validationMessage = '';
    //   this.endpoint=`http://localhost:8084/search/train/source/${this.sourceStation}/destination/${this.destinationStation}/date/${this.date}`;
    //   //this.validationMessage = this.endpoint;

    // }
    //this.http.post('http://localhost:8082/login', loginData).subscribe(
      this.dataService.postData(endpoint,trainData).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        if (response) {
          console.log('Registration successful:', response);
          this.successMessage = 'Train added successfully.';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          //localStorage.setItem('user', JSON.stringify(response));
          // setTimeout(() => {
          //   this.router.navigate(['/login']); // redirecting
          // },2000);
        } else {
          // Handle case where response is null (wrong username or password)
          this.errorMessage = 'Could not add train.';
        }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.errorMessage = 'Could not connect to server. Please try again later.';
      }
    );
  }


  // public method
  // SignUpOptions = [
  //   {
  //     image: 'assets/images/authentication/google.svg',
  //     name: 'Google'
  //   },
  //   {
  //     image: 'assets/images/authentication/twitter.svg',
  //     name: 'Twitter'
  //   },
  //   {
  //     image: 'assets/images/authentication/facebook.svg',
  //     name: 'Facebook'
  //   }
  // ];
}

