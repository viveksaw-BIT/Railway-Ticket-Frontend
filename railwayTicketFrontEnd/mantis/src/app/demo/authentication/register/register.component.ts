// angular import
import { Component } from '@angular/core';
import { RouterModule,Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { SharedModule } from '../../../theme/shared/shared.module';
import { DataService } from 'src/app/service/data.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterModule,SharedModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export default class RegisterComponent {

  firstName: string = '';
  lastName: string = '';
  userName: string = '';
  password: string = '';
  repeatPassword: string = '';
  passwordMismatch: boolean = false;
  userType: number = 1; // Using 1 for regular users and 0 for admin - only allowing new regular user registration

  errorMessage: string = '';
  successMessage: string = '';

  constructor(private dataService: DataService, private http: HttpClient,private router: Router) {}

  onSubmit() {

    const endpoint = 'http://localhost:8082/register';

    const registerData = {
      user_fname: this.firstName,
      user_lname: this.lastName,
      username: this.userName,
      password: this.password,
      user_type: this.userType
    };

    //this.http.post('http://localhost:8082/login', loginData).subscribe(
      this.dataService.postData(endpoint,registerData).subscribe(
      (response) => {
        //console.log('Login successful:', response);
        // Handle successful login response
        if (response) {
          console.log('Registration successful:', response);
          this.successMessage = 'Registration successful. Redirecting to Login ...';
          // Handle successful login response
          // Redirect the user to a new URL
          // Storing the user data in local storage
          localStorage.setItem('user', JSON.stringify(response));
          setTimeout(() => {
            this.router.navigate(['/login']); // redirecting
          },2000);
        } else {
          // Handle case where response is null (wrong username or password)
          this.errorMessage = 'Could not create account. Please try again.';
        }

      },
      (error) => {
        console.error('Login error:', error);
        // Handle login error
        this.errorMessage = 'Could not connect to server. Please try again later.';
      }
    );
  }

  checkPasswords() {
    this.passwordMismatch = this.password !== this.repeatPassword;
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
