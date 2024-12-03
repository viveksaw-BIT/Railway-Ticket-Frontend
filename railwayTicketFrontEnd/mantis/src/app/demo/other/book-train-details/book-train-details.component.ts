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
  selector: 'app-book-train-details',
  standalone: true,
  imports: [CommonModule,SharedModule],
  templateUrl: './book-train-details.component.html',
  styleUrl: './book-train-details.component.scss'
})
export class BookTrainDetailsComponent implements OnInit{
  @Input() endpoint: string = ''; // Input property for dynamic endpoint
  @Input() trainId: number;
  @Input() date: string = '';
  @Input() sourceStation: string = '';
  @Input() destinationStation: string = '';
  data: any; // Variable to hold the fetched data
  filteredData: any[] = []; // Filtered data based on search
  //filteredData: any[] = tableData; // Filtered data based on search
  searchText: string = ''; // Search text
  currentPage: number = 1; // Current page
  itemsPerPage: number = 10; // Items per page
  rowsPerPageOptions: number[] = [10, 20, 50, 100]; // Options for rows per page

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

  //
  bookTicket(trainId: number, trainName: string, date: string, sourceStation: string, destinationStation: string): void {
    // Navigate to BookTicketComponent with train ID and additional data
    // this.router.navigate(['/book-ticket', trainId], { queryParams: { additionalData: additionalData } });
    this.router.navigate(['/book-train', trainId, trainName, date, sourceStation, destinationStation]);
  }

  //

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
