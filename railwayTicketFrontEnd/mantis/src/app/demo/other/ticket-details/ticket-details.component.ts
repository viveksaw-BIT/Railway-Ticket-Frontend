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
import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

@Component({
  selector: 'app-ticket-details',
  standalone: true,
  imports: [RouterModule,SharedModule,BookTrainDetailsComponent],
  templateUrl: './ticket-details.component.html',
  styleUrls: ['./ticket-details.component.scss']
})
export default class TicketDetailsComponent implements OnInit{

  bookingId: number;
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
  bookingDetails: any;

  showModal = false;
  successModal = false;
  message: string = '';

  bookingPNRToCancel: number;

  userData = JSON.parse(localStorage.getItem('user') || '{}');

  
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
    // const endpoint = 'http://localhost:8083/stations';
    
    
    // // Retrieve the parameters from the route
    this.bookingId = this.route.snapshot.paramMap.get('bookingId') as unknown as number;
    console.log(this.bookingId+' - this is booking id');
    // // this.trainName = this.route.snapshot.paramMap.get('trainName');
    // // this.date = this.route.snapshot.paramMap.get('date');
    // // this.sourceStationId = this.route.snapshot.paramMap.get('sourceStation');
    // // this.destinationStationId = this.route.snapshot.paramMap.get('destinationStation');

    // //http://localhost:8084/search/train/source/1/destination/3/date/2024-08-02
    // //http://localhost:8084/search/trains/source/Station 1A/destination/Station 1C/date/2024-08-01
    // this.dataService.fetchData(endpoint).subscribe(
    //   (response) => {
    //     this.stationOptions = response; // Assign the response to the data variable
    //     //this.filteredData = response;
    //     this.assignStationNames();
    //   },
    //   (error) => {
    //     console.error('Error fetching data:', error);
    //   }
    // );

    //
    const bookingDetailEndpoint = `http://localhost:8085/api/bookings/bookingid/${this.bookingId}`;
    console.log(bookingDetailEndpoint);

    this.dataService.fetchData(bookingDetailEndpoint).subscribe(
      (response) => {
        this.bookingDetails = response; // Assign the response to the data variable
        console.log(this.bookingDetails);
        //this.filteredData = response;
        //this.assignTrainDetails();
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

    // this.trainId = this.bookingDetails.trainId;

    // const trainDetailEndpoint = `http://localhost:8084/search/train/id/${this.trainId}`;

    // this.dataService.fetchData(trainDetailEndpoint).subscribe(
    //   (response) => {
    //     this.trainDetails = response; // Assign the response to the data variable
    //     //this.filteredData = response;
    //     //this.assignTrainDetails();
    //     //this.assignStationNames();
    //     //this.trainName = this.trainDetails[0].name;
    //     // this.date = this.route.snapshot.paramMap.get('date');
    //     // this.sourceStationId = this.route.snapshot.paramMap.get('sourceStation');
    //     // this.destinationStationId = this.route.snapshot.paramMap.get('destinationStation');

    //   },
    //   (error) => {
    //     console.error('Error fetching data:', error);
    //   }
    // );
    

    // console.log(this.stationOptions);
    
    
  }

  numberOfPassengers: number = 1; // Default to 1 passenger
  passengers: { name: string; age: number; gender: string }[] = []; // Array to hold passenger details

  openCancelBookingModal(bookingPNR: number): void {
    this.showModal = true;
    this.bookingPNRToCancel = bookingPNR;
  }
  //cancel train
  cancelTicket(): void{
    const endpoint = `http://localhost:8085/api/bookings/${this.bookingPNRToCancel}`;
    this.dataService.updateData(endpoint,"CANCELLED").subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        // if (response) {
          console.log('Cancellation successful:', response);
          this.message = 'Booking cancelled successfully.';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          //localStorage.setItem('user', JSON.stringify(response));
          this.successModal = true;
          setTimeout(() => {
               this.router.navigate(['/booking-details']); // redirecting
             },2000);
          
          // const endpoint = `http://localhost:8085/api/bookings/user/${this.userData.id}`;
          // this.dataService.fetchData(endpoint).subscribe(
          //   (response) => {
          //     this.data = response; // Assign the response to the data variable
          //     this.filteredData = response;
          //   },
          //   (error) => {
          //     console.error('Error fetching data:', error);
          //   }
          // );
          //  setTimeout(() => {
          //    this.router.navigate(['/all-trains']); // redirecting
          //  },2000);
        // } else {
        //   // Handle case where response is null (wrong username or password)
        //   this.message = 'Could not delete train.';
        //   this.successModal = true;
        // }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.message = 'Could not cancel booking';
        console.log(this.message);
        this.successModal = true;
      }
    );

  }
  
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
    const endpoint = 'http://localhost:8085/api/bookings/1';
    

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

  // cancelTicket(bookingId: number): void {
  //   // Navigate to BookTicketComponent with train ID and additional data
  //   // this.router.navigate(['/book-ticket', trainId], { queryParams: { additionalData: additionalData } });
  //   // this.router.navigate(['/book-train', trainId, trainName, date, sourceStation, destinationStation]);
  //   console.log(bookingId+ 'cancelled');
  // }

  // downloadTicket() {
  //   const doc = new jsPDF();
  
  //   doc.setFontSize(18);
  //   doc.text('Booking Details', 20, 10);
  
  //   doc.setFontSize(12);
  //   doc.text(`Booking ID: BKG-${this.bookingDetails.id}`, 20, 20);
  //   doc.text(`Train Name: ${this.bookingDetails.train.name}`, 20, 30);
  //   // Add more booking details as needed
  
  //   doc.save(`booking-${this.bookingDetails.id}.pdf`);
  // }

  //for image generation

  // downloadTicket() {
  //   const data = document.getElementById('content-to-print'); // Get the HTML element
  
  //   html2canvas(data!).then(canvas => {
  //     const imgData = canvas.toDataURL('image/png'); // Convert canvas to image data
  //     const pdf = new jsPDF();
  //     const imgWidth = 190; // Set the width of the image in the PDF
  //     const pageHeight = pdf.internal.pageSize.height;
  //     const imgHeight = (canvas.height * imgWidth) / canvas.width;
  //     let heightLeft = imgHeight;
  
  //     let position = 25;
  
  //     // Add the image to the PDF
  //     pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
  //     heightLeft -= pageHeight;
  
  //     // Add new pages if the content is longer than one page
  //     while (heightLeft >= 0) {
  //       position = heightLeft - imgHeight;
  //       pdf.addPage();
  //       pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
  //       heightLeft -= pageHeight;
  //     }
  
  //     pdf.save(`booking-${this.bookingDetails.id}.pdf`); // Save the PDF
  //   });
  // }

  downloadTicket() {
    // Create a temporary div to hold the new HTML content
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = `
      <h2 style="text-align: center; font-weight: 800; color: #FFFFFF;">Online Railway Ticket Booking Portal</h2>
      <p style="text-align: center; margin: 10px ; color: #FFFFFF;">Thank you for flying with us.</p>
    `;

    // Create a footer div to hold the new HTML content
    const footerDiv = document.createElement('div');
    footerDiv.innerHTML = `
      <p style="text-align: center; margin-top: 20px ; color: #FFFFFF;">Copyright &copy; Online Railway Ticket Booking Portal</p>
    `;


    // Create a new parent div
    const borderDiv = document.createElement('div');
    borderDiv.style.border = '1px solid black'; // Add a border to the parent div
    borderDiv.style.padding = '20px';
    borderDiv.style.margin = '25px 20px 0px 20px';
    borderDiv.style.background = '#FBFAFC';
    borderDiv.style.borderRadius = '8px';

  
    // Get the existing content to print
    const data = document.getElementById('content-to-print');

    borderDiv.appendChild(data.cloneNode(true));
    
    // Append the temporary content to the existing content
    const combinedContent = document.createElement('div');
    combinedContent.appendChild(tempDiv);
    combinedContent.style.padding = '20px';
    //combinedContent.style.background = '#fafafa';
    combinedContent.style.background = '#0C00C9';
    if (data) {
      combinedContent.appendChild(borderDiv.cloneNode(true)); // Clone the existing content
      combinedContent.appendChild(footerDiv.cloneNode(true));
    }
  
    // Append combined content to the body temporarily
    document.body.appendChild(combinedContent);
  
    // Use html2canvas to capture the combined content
    html2canvas(combinedContent).then(canvas => {
      const imgData = canvas.toDataURL('image/png'); // Convert canvas to image data
      const pdf = new jsPDF();
      const imgWidth = 190; // Set the width of the image in the PDF
      const imgHeight = (canvas.height * imgWidth) / canvas.width; // Calculate height based on aspect ratio
      const pageHeight = pdf.internal.pageSize.height;
      let heightLeft = imgHeight;
  
      let position = 20;
  
      // Add the image to the PDF
      pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
      heightLeft -= pageHeight;
  
      // Add new pages if the content is longer than one page
      while (heightLeft >= 0) {
        position = heightLeft - imgHeight;
        pdf.addPage();
        pdf.addImage(imgData, 'PNG', 10, position, imgWidth, imgHeight);
        heightLeft -= pageHeight;
      }
  
      pdf.save(`ticket-${this.bookingDetails.pnr}.pdf`); // Save the PDF
  
      // Remove the temporary content from the body
      document.body.removeChild(combinedContent);
    }).catch(err => {
      console.error('Error generating PDF:', err);
      // Remove the temporary content in case of error
      document.body.removeChild(combinedContent);
    });
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

