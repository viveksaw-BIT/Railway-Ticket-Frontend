

import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

interface RouteData {
  allowedUserTypes: string[]; // Define the allowed user types as an array of strings
}

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    //const userData: any = localStorage.getItem('userData');
    const userData = JSON.parse(localStorage.getItem('user') || '{}');
    // if(Object.keys(userData).length !== 0)
    // {
    //   console.log("Yes Data");
    // }
    // else{
    //   console.log("No Data");
    // }
    
    

    // Access the allowedUserTypes using bracket notation
    const allowedUserTypes = (route.data as RouteData).allowedUserTypes;
    
    // // Define the allowed user types for each route
    // const allowedUserTypes = route.data.allowedUserTypes;
    //console.log(allowedUserTypes);
    if(Object.keys(userData).length !== 0){
      const userType = (userData.user_type).toString();
      // const userType = (userData.user_type).toString();
      console.log("Inside UserData");
      if(userType=="0" || userType=="1")
      {
        console.log("UserType either 0 or 1");
        if (allowedUserTypes && allowedUserTypes.includes(userType)) {
          return true; // Allow access
        }
        else {
          // Redirect to dashboard if userType is not found
          this.router.navigate(['']);
          return false;
        }
      }
      else {
        // Redirect to login if userType is not found
        this.router.navigate(['/login']);
        return false;
      }
    }
    else {
      console.log("User not in storage");
      // Redirect to login if userType is not found
      this.router.navigate(['/home']);
      return false;
    }
  }
}



// import { Injectable } from '@angular/core';
// import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

// @Injectable({
//   providedIn: 'root'
// })
// export class AuthGuard implements CanActivate {
//   constructor(private router: Router) {}

//   canActivate(
//     route: ActivatedRouteSnapshot,
//     state: RouterStateSnapshot
//   ): boolean {
//     //const userData: any = localStorage.getItem('userData');
//     const userData = JSON.parse(localStorage.getItem('user') || '{}');
//     console.log(userData.user_type);

//     // Check if user is admin (0) or normal user (1)
//     if (userData.user_type === 0) {
//       // Allow access for admin
//       return true;
//     } else if (userData.user_type === 1) {
//       // Redirect normal users to their dashboard or another route
//       this.router.navigate(['/dashboard/default']);
//       return false;
//     } else {
//       // Redirect to login if userType is not found
//       this.router.navigate(['/login']);
//       return false;
//     }
//   }
// }