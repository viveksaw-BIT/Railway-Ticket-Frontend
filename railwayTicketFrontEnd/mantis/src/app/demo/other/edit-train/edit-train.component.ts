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
import { RouterModule,Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SharedModule } from '../../../theme/shared/shared.module';
import { DataService } from 'src/app/service/data.service';
import { TrainDetailsComponent } from '../../default/dashboard/train-details/train-details.component';

@Component({
  selector: 'app-edit-train',
  standalone: true,
  imports: [RouterModule,SharedModule,TrainDetailsComponent],
  templateUrl: './edit-train.component.html',
  styleUrls: ['./edit-train.component.scss']
})
export default class EditTrainComponent implements OnInit{

  trainId: number;
  trainDetails: any;
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

  constructor(private dataService: DataService, private http: HttpClient,private router: Router,private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.trainId = this.route.snapshot.paramMap.get('trainId') as unknown as number;
    
    const endpoint = 'http://localhost:8083/routes';
    const trainDetailEndpoint = `http://localhost:8084/search/train/id/${this.trainId}`;
    
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

    this.dataService.fetchData(trainDetailEndpoint).subscribe(
      (response) => {
        this.trainDetails = response; // Assign the response to the data variable
        //this.filteredData = response;
        console.log(this.trainDetails);
        this.assignTrainDetails();
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  onSubmit() {

    const endpoint = `http://localhost:8083/trains/${this.trainId}`;

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
      this.dataService.updateData(endpoint,trainData).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        if (response) {
          console.log('Registration successful:', response);
          this.successMessage = 'Train edited successfully.';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          //localStorage.setItem('user', JSON.stringify(response));
           setTimeout(() => {
             this.router.navigate(['/all-trains']); // redirecting
           },2000);
        } else {
          // Handle case where response is null (wrong username or password)
          this.errorMessage = 'Could not edit train.';
        }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.errorMessage = 'Could not edit train as there are one or more bookings available.';
      }
    );
  }

  assignTrainDetails(): void {
    for(let item of this.trainDetails){
      this.trainName = item.name;
      this.trainNumber = item.number;
      this.trainRoute = item.route.id;
      this.date = item.trainSchedules[0].date;
      this.departureTime = item.trainSchedules[0].departureTime;
      this.arrivalTime = item.trainSchedules[0].arrivalTime;
      this.totalSeats = item.segments[0].totalSeats;
    }
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

