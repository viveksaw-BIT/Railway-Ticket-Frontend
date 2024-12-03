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
import { BookTrainDetailsComponent } from '../book-train-details/book-train-details.component';

@Component({
  selector: 'app-book-train',
  standalone: true,
  imports: [RouterModule,SharedModule,BookTrainDetailsComponent],
  templateUrl: './book-train.component.html',
  styleUrls: ['./book-train.component.scss']
})
export default class BookTrainComponent implements OnInit{

  trainId: number;

  sourceStationId: string = '';
  destinationStationId: string = '';

  endpoint: string = '';
  sourceStation: string = '';
  destinationStation: string = '';
  departureTime: string = '';
  arrivalTime: string = '';
  trainName: string = '';
  trainNumber: string = '';
  totalSeats: string = '';
  trainRoute: string = '';

  seatClass: string = '';
  quota: string = '';

  trainDetails: any;

  logUserData = JSON.parse(localStorage.getItem('user') || '{}');

  
  // sourceStation: number;
  // destinationStation: number;
  //stationOptions: string[] = [];
  //trainRoutes: any;
  stationOptions: any;
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

  constructor(private dataService: DataService, private http: HttpClient,private router: Router,private route: ActivatedRoute) {
    this.passengers = Array.from({ length: this.numberOfPassengers }, () => ({
      name: '',
      age: null,
      gender: ''
    }));
  }

  ngOnInit(): void {
    const endpoint = 'http://localhost:8083/stations';
    
    
    // Retrieve the parameters from the route
    this.trainId = this.route.snapshot.paramMap.get('trainId') as unknown as number;
    // this.trainName = this.route.snapshot.paramMap.get('trainName');
    // this.date = this.route.snapshot.paramMap.get('date');
    this.sourceStationId = this.route.snapshot.paramMap.get('sourceStation');
    this.destinationStationId = this.route.snapshot.paramMap.get('destinationStation');

    //http://localhost:8084/search/train/source/1/destination/3/date/2024-08-02
    //http://localhost:8084/search/trains/source/Station 1A/destination/Station 1C/date/2024-08-01
    this.dataService.fetchData(endpoint).subscribe(
      (response) => {
        this.stationOptions = response; // Assign the response to the data variable
        //this.filteredData = response;
        this.assignStationNames();
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );

    //
    const trainDetailEndpoint = `http://localhost:8084/search/train/id/${this.trainId}`;

    this.dataService.fetchData(trainDetailEndpoint).subscribe(
      (response) => {
        this.trainDetails = response; // Assign the response to the data variable
        //this.filteredData = response;
        this.assignTrainDetails();
        //this.assignStationNames();
        //this.trainName = this.trainDetails[0].name;
        // this.date = this.route.snapshot.paramMap.get('date');
        // this.sourceStationId = this.route.snapshot.paramMap.get('sourceStation');
        // this.destinationStationId = this.route.snapshot.paramMap.get('destinationStation');

      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
    

    // console.log(this.stationOptions);
    
    
  }
  numberOfPassengers: number = 1; // Default to 1 passenger
  passengers: { name: string; age: number; gender: string }[] = []; // Array to hold passenger details

  assignStationNames(): void {
    for(let item of this.stationOptions)
      {
        if(item.id==this.sourceStationId)
          this.sourceStation = item.name;
        if(item.id==this.destinationStationId)
          this.destinationStation = item.name;
      }
  }

  assignTrainDetails(): void {
    for(let item of this.trainDetails)
    {
      this.trainName = item.name;
      this.date = item.trainSchedules[0].date;
      this.departureTime = item.trainSchedules[0].departureTime;
      this.arrivalTime = item.trainSchedules[0].arrivalTime;
    }
  }


  // Method to update the number of passengers and reset the passengers array
  updatePassengerCount(): void {
    if (this.numberOfPassengers < 1) {
      this.numberOfPassengers = 1; // Ensure minimum is 1
    } else if (this.numberOfPassengers > 6) {
      this.numberOfPassengers = 6; // Ensure maximum is 6
    }

    // Reset passengers array based on the number entered
    this.passengers = Array.from({ length: this.numberOfPassengers }, () => ({
      name: '',
      age: null,
      gender: ''
    }));
  }

  // Method to handle form submission
  // onSubmit(): void {
  //   console.log('Passenger Details:', this.passengers);
    // Handle form submission logic here
 // }
  

//-------------

onSubmit(): void {

    //const endpoint = 'http://localhost:8083/trains';
    const endpoint = `http://localhost:8085/api/bookings/${this.logUserData.id}`;
    

    const bookingData = {
      trainId: this.trainId,
      journeyDate: this.date,
      sourceStation: this.sourceStation,
      destinationStation: this.destinationStation,
      seatClass: this.seatClass,
      quota: this.quota,
      //"totalFare": 150.0,
      bookingStatus: "CONFIRMED",
      passengers: this.passengers,
  }

  console.log(JSON.stringify(bookingData));
    //this.http.post('http://localhost:8082/login', loginData).subscribe(
      this.dataService.postData(endpoint,bookingData).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        if (response) {
          console.log('Registration successful:', response);
          this.successMessage = 'Ticket booked successfully.';
          this.errorMessage='';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          //localStorage.setItem('user', JSON.stringify(response));
          // setTimeout(() => {
          //   this.router.navigate(['/login']); // redirecting
          // },2000);
        } else {
          // Handle case where response is null (wrong username or password)
          this.errorMessage = 'Could not book ticket.';
        }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.errorMessage = 'Could not connect to server. Please try again later.';
      }
    );
  }

//-------------

  // onSubmit() {

  //   const endpoint = 'http://localhost:8083/trains';
    //const endpoint = 'http://localhost:8085/api/bookings/1';
    

  //   const trainData = {
  //       name: this.trainName,
  //       number: this.trainNumber,
  //       routeId: this.trainRoute,
  //       scheduleDate: this.date,
  //       departureTime: this.departureTime,
  //       arrivalTime: this.arrivalTime,
  //       totalSeats: this.totalSeats
  //   };
  //   // if(Number(this.sourceStation)>=Number(this.destinationStation)){
  //   //   if(Number(this.sourceStation)>Number(this.destinationStation))
  //   //     this.validationMessage = 'No train available for the selected stations';
  //   //   else
  //   //   this.validationMessage = 'Source and Destination stations cannot be same';
  //   // }
  //   // else{
  //   //    this.validationMessage = '';
  //   //   this.endpoint=`http://localhost:8084/search/train/source/${this.sourceStation}/destination/${this.destinationStation}/date/${this.date}`;
  //   //   //this.validationMessage = this.endpoint;

  //   // }
  //   //this.http.post('http://localhost:8082/login', loginData).subscribe(
  //     this.dataService.postData(endpoint,trainData).subscribe(
  //     (response) => {
  //       //console.log('Login successful:', response);
  //       // Handle successful login response
  //       if (response) {
  //         console.log('Registration successful:', response);
  //         this.successMessage = 'Train added successfully.';
  //         // Handle successful login response
  //         // Redirect the user to a new URL
  //         // Storing the user data in local storage
  //         //localStorage.setItem('user', JSON.stringify(response));
  //         // setTimeout(() => {
  //         //   this.router.navigate(['/login']); // redirecting
  //         // },2000);
  //       } else {
  //         // Handle case where response is null (wrong username or password)
  //         this.errorMessage = 'Could not add train.';
  //       }

  //     },
  //     (error) => {
  //       console.error('Login error:', error);
  //       // Handle login error
  //       this.errorMessage = 'Could not connect to server. Please try again later.';
  //     }
  //   );
  // }


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

