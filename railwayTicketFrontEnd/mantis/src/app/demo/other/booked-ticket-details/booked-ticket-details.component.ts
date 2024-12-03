import { Component, Input, OnInit } from '@angular/core';
import tableData from 'src/fake-data/default-data.json';
import { DataService } from 'src/app/service/data.service';
import { CommonModule } from '@angular/common';
import { RouterModule,Router } from '@angular/router';


import fakeTableData from 'src/fake-data/default-data.json';

import { IconService } from '@ant-design/icons-angular';
import { MenuUnfoldOutline, MenuFoldOutline, SearchOutline } from '@ant-design/icons-angular/icons';
import { SharedModule } from 'src/app/theme/shared/shared.module';

@Component({
  selector: 'app-booked-ticket-details',
  standalone: true,
  imports: [CommonModule,SharedModule],
  templateUrl: './booked-ticket-details.component.html',
  styleUrl: './booked-ticket-details.component.scss'
})
export class BookedTicketDetailsComponent implements OnInit{
  // @Input() endpoint: string = ''; // Input property for dynamic endpoint
  // @Input() trainId: number;
  // @Input() date: string = '';
  // @Input() sourceStation: string = '';
  // @Input() destinationStation: string = '';
  currentDate: Date = new Date;
  data: any; // Variable to hold the fetched data
  filteredData: any[] = []; // Filtered data based on search
  //filteredData: any[] = tableData; // Filtered data based on search
  searchText: string = ''; // Search text
  currentPage: number = 1; // Current page
  itemsPerPage: number = 10; // Items per page
  rowsPerPageOptions: number[] = [10, 20, 50, 100]; // Options for rows per page

  showModal = false;
  successModal = false;
  message: string = '';

  bookingPNRToCancel: number;

  userData = JSON.parse(localStorage.getItem('user') || '{}');

  constructor(private dataService: DataService,private iconService: IconService,private router: Router) {
    this.iconService.addIcon(...[MenuUnfoldOutline, MenuFoldOutline, SearchOutline]);
  }

  ngOnInit(): void {
    const endpoint = `http://localhost:8085/api/bookings/user/${this.userData.id}`;
    this.dataService.fetchData(endpoint).subscribe(
      (response) => {
        this.data = response; // Assign the response to the data variable
        this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  //
  // cancelTicket(trainId: number): void {
  //   // Navigate to BookTicketComponent with train ID and additional data
  //   // this.router.navigate(['/book-ticket', trainId], { queryParams: { additionalData: additionalData } });
  //   // this.router.navigate(['/book-train', trainId, trainName, date, sourceStation, destinationStation]);
  //   console.log(trainId+ 'cancelled');
  // }

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
          
          const endpoint = `http://localhost:8085/api/bookings/user/${this.userData.id}`;
          this.dataService.fetchData(endpoint).subscribe(
            (response) => {
              this.data = response; // Assign the response to the data variable
              this.filteredData = response;
            },
            (error) => {
              console.error('Error fetching data:', error);
            }
          );
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

  viewTicket(bookingId: number): void {
    // Navigate to BookTicketComponent with train ID and additional data
    // this.router.navigate(['/book-ticket', trainId], { queryParams: { additionalData: additionalData } });
    this.router.navigate(['/ticket-details', bookingId]);
    console.log(bookingId+ 'view');
  }

  //

  // ngOnChanges(): void {
  //   // Fetch data whenever the endpoint changes
  //   if (this.endpoint) {
  //     this.dataService.fetchData(this.endpoint).subscribe(
  //       (response) => {
  //         this.data = response; // Assign the response to the data variable
  //         this.filteredData = response;
  //       },
  //       (error) => {
  //         console.error('Error fetching data:', error);
  //       }
  //     );
  //   }
  // }

  // search(): void {
  //   this.filteredData = this.data.filter(item =>
  //     item.name.toLowerCase().includes(this.searchText.toLowerCase())
  //   );
  //   this.currentPage = 1; // Reset to the first page after search
  // }
  search(): void {
    this.filteredData = this.data.filter(item =>
      item.pnr.toLowerCase().includes(this.searchText.toLowerCase())
    );
    this.currentPage = 1; // Reset to the first page after search
  }

  onPageChange(page: number): void {
    this.currentPage = page;
  }

  updateRowsPerPage(): void {
    this.currentPage = 1; // Reset to the first page after changing rows per page
  }

  getPageCount(): number {
    return Math.ceil(this.filteredData.length / this.itemsPerPage);
  }

  getPageNumbers(): number[] {
    const pageCount = this.getPageCount();
    return Array.from({ length: pageCount }, (_, i) => i + 1);
  }

  // New method to calculate the ending index
  getEndingIndex(): number {
    return Math.min(this.currentPage * this.itemsPerPage, this.filteredData.length);
  }

  trainDetails = tableData;

}

// Example method that requires authentication -- keeping for future reference
// getProtectedData(): Observable<any> {
//   const userData = JSON.parse(localStorage.getItem('user') || '{}');
//   const headers = new HttpHeaders({
//     'Authorization': `Bearer ${userData.token}` // Assuming the token is stored in the user object
//   });

//   return this.http.get(`${this.apiUrl}/protected`, { headers });
// }

// export class TrainDetailsComponent implements OnInit{
//   data: any; // Variable to hold the fetched data

//   constructor(private dataService: DataService) {}

//   ngOnInit(): void {
//     this.dataService.fetchData().subscribe(
//       (response) => {
//         this.data = response; // Assign the response to the data variable
//       },
//       (error) => {
//         console.error('Error fetching data:', error);
//       }
//     );
//   }

//   trainDetails = tableData;
// }
