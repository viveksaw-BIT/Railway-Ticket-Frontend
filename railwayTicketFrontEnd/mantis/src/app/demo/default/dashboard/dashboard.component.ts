// angular import
import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';

// project import
import tableData from 'src/fake-data/default-data.json';
import { SharedModule } from 'src/app/theme/shared/shared.module';
import { MonthlyBarChartComponent } from './monthly-bar-chart/monthly-bar-chart.component';
import { IncomeOverviewChartComponent } from './income-overview-chart/income-overview-chart.component';
import { AnalyticsChartComponent } from './analytics-chart/analytics-chart.component';
import { SalesReportChartComponent } from './sales-report-chart/sales-report-chart.component';
import { HttpClient } from '@angular/common/http';
import { RouterModule,Router } from '@angular/router';
import { DataService } from 'src/app/service/data.service';

// icons
import { IconService } from '@ant-design/icons-angular';
import { FallOutline, GiftOutline, MessageOutline, RiseOutline, SettingOutline } from '@ant-design/icons-angular/icons';
//import { TrainDetailsComponent } from './train-details/train-details.component';

@Component({
  selector: 'app-default',
  standalone: true,
  imports: [
    CommonModule,
    SharedModule,
    MonthlyBarChartComponent,
    IncomeOverviewChartComponent,
    AnalyticsChartComponent,
    SalesReportChartComponent,
    //TrainDetailsComponent
  ],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DefaultComponent implements OnInit{
  // constructor
  constructor(private iconService: IconService,private dataService: DataService, private http: HttpClient,private router: Router) {
    this.iconService.addIcon(...[RiseOutline, FallOutline, SettingOutline, GiftOutline, MessageOutline]);
  }

  errorMessage: string = '';
  successMessage: string = '';
  validationMessage: string = '';

  trainData: any;
  userData: any;
  bookingData: any;
  bookingUserData: any;
  salesTodayData: number = 0;
  trainsTodayData: any = [];
  bookingsTodayData: any = [];
  bookingsTodayUserData: any = [];
  //currentDate: Date = new Date;
  //todayDate = this.currentDate.toLocaleDateString();
  todayDate: string = (new Date).toISOString().split('T')[0]; // Get 'YYYY-MM-DD'

  logUserData: any;

  // showModal = false;

  // deleteTrain() {
  //   // Add your delete logic here
  //   console.log('Train deleted');
  // }
  

  ngOnInit(): void {
    this.logUserData = JSON.parse(localStorage.getItem('user') || '{}');
    const trainEndpoint = 'http://localhost:8083/trains';
    const userEndpoint = 'http://localhost:8082/users';
    const bookingEndpoint = 'http://localhost:8085/api/bookings';
    const bookingUserEndpoint = `http://localhost:8085/api/bookings/user/${this.logUserData.id}`;
    //console.log(bookingUserEndpoint);

    
    
    this.dataService.fetchData(trainEndpoint).subscribe(
      (response) => {
        this.trainData = response; // Assign the response to the data variable
        this.checkTrainData();
        this.checkTrainUserData();
        // console.log(this.minDate);
        // this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );

    this.dataService.fetchData(userEndpoint).subscribe(
      (response) => {
        this.userData = response; // Assign the response to the data variable
        this.checkUserData();
        //console.log(this.minDate);
        //this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );

    this.dataService.fetchData(bookingEndpoint).subscribe(
      (response) => {
        this.bookingData = response; // Assign the response to the data variable
        this.checkBookingData();
        //this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );

    this.dataService.fetchData(bookingUserEndpoint).subscribe(
      (response) => {
        this.bookingUserData = response; // Assign the response to the data variable
        this.checkBookingUserData();
        //this.filteredData = response;
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );

  }


  checkTrainData(): void {
    // console.log(this.todayDate);
    for(let train of this.trainData)
    {
      console.log(train.trainSchedules[0].date);

      if(train.trainSchedules[0].date == this.todayDate){
        
        this.trainsTodayData.push(train);
      }
    }
    this.AnalyticAdmin[0].amount = this.trainsTodayData.length;

    //console.log(this.trainsTodayData);
  }

  checkUserData(): void {
    this.AnalyticAdmin[1].amount = (this.userData.length-1).toString();
  }

  checkBookingData(): void {
    for(let booking of this.bookingData)
      {
        //console.log(train.trainSchedules[0].date);
        if(booking.journeyDate == this.todayDate && booking.bookingStatus!= 'CANCELLED'){
          this.bookingsTodayData.push(booking);
        }
      }
    this.AnalyticAdmin[2].amount = this.bookingsTodayData.length;
    for(let booking of this.bookingsTodayData)
    {
      //console.log(booking.totalFare);
      this.salesTodayData = this.salesTodayData + booking.totalFare;
    }
    //console.log(this.salesTodayData);
    this.AnalyticAdmin[3].amount = this.salesTodayData.toString() + ' INR';
  }

  //--------------------------

  checkTrainUserData(): void {
    // for(let train of this.trainData)
    // {
    //   //console.log(train.trainSchedules[0].date);
    //   if(train.trainSchedules[0].date == this.todayDate){
    //     this.trainsTodayData.push(train);
    //   }
    // }
    this.AnalyticUser[0].amount = this.trainsTodayData.length;
    //console.log(this.trainsTodayData);
  }

  // checkUserData(): void {
  //   this.AnalyticAdmin[1].amount = (this.userData.length-1).toString();
  // }

  checkBookingUserData(): void {
    for(let booking of this.bookingUserData)
      {
        // console.log(booking);
        if(booking.journeyDate == this.todayDate && booking.bookingStatus!= 'CANCELLED'){
          this.bookingsTodayUserData.push(booking);
        }
      }
    this.AnalyticUser[1].amount = this.bookingsTodayUserData.length;
    // for(let booking of this.bookingsTodayUserData)
    // {
    //   //console.log(booking.totalFare);
    //   this.salesTodayData = this.salesTodayData + booking.totalFare;
    // }
    // //console.log(this.salesTodayData);
    // this.AnalyticAdmin[3].amount = this.salesTodayData.toString() + ' INR';
  }


  trainDetails = tableData;

  AnalyticAdmin = [
    {
      title: 'Total Trains',
      amount: '0',
      background: 'bg-light-primary ',
      border: 'border-primary',
      icon: 'rise',
      percentage: '59.3%',
      color: 'text-primary',
      number: '35,000'
    },
    {
      title: 'Total Users',
      amount: '0',
      background: 'bg-light-primary ',
      border: 'border-primary',
      icon: 'rise',
      percentage: '70.5%',
      color: 'text-primary',
      number: '8,900'
    },
    {
      title: 'Total Bookings',
      amount: '0',
      background: 'bg-light-warning ',
      border: 'border-warning',
      icon: 'fall',
      percentage: '27.4%',
      color: 'text-warning',
      number: '1,943'
    },
    {
      title: 'Total Sales',
      amount: '0',
      background: 'bg-light-warning ',
      border: 'border-warning',
      icon: 'fall',
      percentage: '27.4%',
      color: 'text-warning',
      number: '$20,395'
    }
  ];

  AnalyticUser = [
    {
      title: 'Total Trains',
      amount: '0',
      background: 'bg-light-primary ',
      border: 'border-primary',
      icon: 'rise',
      percentage: '59.3%',
      color: 'text-primary',
      number: '35,000'
    },
    {
      title: 'Total Bookings',
      amount: '0',
      background: 'bg-light-primary ',
      border: 'border-primary',
      icon: 'rise',
      percentage: '70.5%',
      color: 'text-primary',
      number: '8,900'
    },
    // {
    //   title: 'Total Bookings',
    //   amount: '0',
    //   background: 'bg-light-warning ',
    //   border: 'border-warning',
    //   icon: 'fall',
    //   percentage: '27.4%',
    //   color: 'text-warning',
    //   number: '1,943'
    // },
    // {
    //   title: 'Total Sales',
    //   amount: '0',
    //   background: 'bg-light-warning ',
    //   border: 'border-warning',
    //   icon: 'fall',
    //   percentage: '27.4%',
    //   color: 'text-warning',
    //   number: '$20,395'
    // }
  ];

  // transaction = [
  //   {
  //     background: 'text-success bg-light-success',
  //     icon: 'gift',
  //     title: 'Order #002434',
  //     time: 'Today, 2:00 AM',
  //     amount: '+ $1,430',
  //     percentage: '78%'
  //   },
  //   {
  //     background: 'text-primary bg-light-primary',
  //     icon: 'message',
  //     title: 'Order #984947',
  //     time: '5 August, 1:45 PM',
  //     amount: '- $302',
  //     percentage: '8%'
  //   },
  //   {
  //     background: 'text-danger bg-light-danger',
  //     icon: 'setting',
  //     title: 'Order #988784',
  //     time: '7 hours ago',
  //     amount: '- $682',
  //     percentage: '16%'
  //   }
  // ];
}
