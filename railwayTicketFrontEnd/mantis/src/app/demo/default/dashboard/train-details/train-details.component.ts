import { Component, Input, OnInit } from '@angular/core';
import tableData from 'src/fake-data/default-data.json';
import { DataService } from 'src/app/service/data.service';
import { CommonModule } from '@angular/common';


import fakeTableData from 'src/fake-data/default-data.json';

import { IconService } from '@ant-design/icons-angular';
import { MenuUnfoldOutline, MenuFoldOutline, SearchOutline } from '@ant-design/icons-angular/icons';
import { SharedModule } from 'src/app/theme/shared/shared.module';
import { Router } from '@angular/router';

@Component({
  selector: 'app-train-details',
  standalone: true,
  imports: [CommonModule,SharedModule],
  templateUrl: './train-details.component.html',
  styleUrl: './train-details.component.scss'
})
export class TrainDetailsComponent implements OnInit{
  @Input() endpoint: string = ''; // Input property for dynamic endpoint
  data: any; // Variable to hold the fetched data
  filteredData: any[] = []; // Filtered data based on search
  //filteredData: any[] = tableData; // Filtered data based on search
  searchText: string = ''; // Search text
  currentPage: number = 1; // Current page
  itemsPerPage: number = 10; // Items per page
  rowsPerPageOptions: number[] = [10, 20, 50, 100]; // Options for rows per page
  successMessage: string = '';
  errorMessage: string = '';
  message: string = '';

  showModal = false;
  successModal = false;

  trainIdToCancel: number;

  userData = JSON.parse(localStorage.getItem('user') || '{}');

  constructor(private dataService: DataService,private iconService: IconService,private router: Router) {
    this.iconService.addIcon(...[MenuUnfoldOutline, MenuFoldOutline, SearchOutline]);
  }

  ngOnInit(): void {
    //const endpoint = 'http://localhost:8083/trains';
    this.dataService.fetchData(this.endpoint).subscribe(
      (response) => {
        this.data = response; // Assign the response to the data variable
        this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
  }

  ngOnChanges(): void {
    // Fetch data whenever the endpoint changes
    if (this.endpoint) {
      this.dataService.fetchData(this.endpoint).subscribe(
        (response) => {
          this.data = response; // Assign the response to the data variable
          this.filteredData = response;
        },
        (error) => {
          console.error('Error fetching data:', error);
        }
      );
    }
  }

  // search(): void {
  //   this.filteredData = this.data.filter(item =>
  //     item.name.toLowerCase().includes(this.searchText.toLowerCase())
  //   );
  //   this.currentPage = 1; // Reset to the first page after search
  // }
  search(): void {
    this.filteredData = this.data.filter(item =>
      item.name.toLowerCase().includes(this.searchText.toLowerCase())
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

  //update train
  updateTrain(trainId: number): void{
    this.router.navigate(['/edit-train', trainId]);
  }

  openCancelTrainModal(trainId: number): void {
    this.showModal = true;
    this.trainIdToCancel = trainId;
  }
  //cancel train
  cancelTrain(): void{
    const endpoint = `http://localhost:8083/trains/${this.trainIdToCancel}`;
    this.dataService.deleteData(endpoint).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        // if (response) {
          console.log('Registration successful:', response);
          this.message = 'Train deleted successfully.';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          //localStorage.setItem('user', JSON.stringify(response));
          this.successModal = true;
          this.dataService.fetchData(this.endpoint).subscribe(
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
        this.message = 'Could not delete train as there are one or more bookings available.';
        console.log(this.message);
        this.successModal = true;
      }
    );

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
