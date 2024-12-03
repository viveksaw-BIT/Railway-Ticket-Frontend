// angular import
import { Component } from '@angular/core';
import { RouterModule,Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SharedModule } from '../../../theme/shared/shared.module';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule,SharedModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export default class LoginComponent {
  
  //---------------
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private dataService: DataService, private http: HttpClient,private router: Router) {}

  

  onSubmit() {

    const endpoint = 'http://localhost:8082/login';

    const loginData = {
      username: this.username,
      password: this.password
    };

    //this.http.post('http://localhost:8082/login', loginData).subscribe(
      this.dataService.postData(endpoint,loginData).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        if (response) {
          console.log('Login successful:', response);
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          localStorage.setItem('user', JSON.stringify(response));
          this.router.navigate(['']); // Change '/dashboard' to your desired route
        } else {
          // Handle case where response is null (wrong username or password)
          this.errorMessage = 'Wrong username or password';
        }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.errorMessage = 'Could not connect to server. Please try again later.';
      }
    );
  }


  //---------------
  
  // public method
  // SignInOptions = [
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
