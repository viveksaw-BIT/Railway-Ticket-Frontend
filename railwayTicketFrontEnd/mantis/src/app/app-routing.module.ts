// angular import
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './service/auth.guard';
// Project import
import { AdminComponent } from './theme/layouts/admin-layout/admin-layout.component';
import { GuestComponent } from './theme/layouts/guest/guest.component';


const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: '',
        redirectTo: '/dashboard/default',
        pathMatch: 'full'
      },
      {
        path: 'dashboard/default',
        loadComponent: () => import('./demo/default/dashboard/dashboard.component').then((c) => c.DefaultComponent),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0','1'] } // Allow user types
      },
      {
        path: 'typography',
        loadComponent: () => import('./demo/ui-component/typography/typography.component')
      },
      {
        path: 'color',
        loadComponent: () => import('./demo/ui-component/ui-color/ui-color.component')
      },
      {
        path: 'sample-page',
        loadComponent: () => import('./demo/other/sample-page/sample-page.component')
      },
      {
        path: 'update-profile',
        loadComponent: () => import('./demo/other/edit-user-profile/edit-user-profile.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0','1'] } // Allow user types
      },
      {
        path: 'search-train',
        loadComponent: () => import('./demo/other/search-train/search-train.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['1'] } // Allow user types
      },
      {
        path: 'add-train',
        loadComponent: () => import('./demo/other/add-train/add-train.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0'] } // Allow user types
      },
      {
        path: 'edit-train/:trainId',
        loadComponent: () => import('./demo/other/edit-train/edit-train.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0'] } // Allow user types
      },
      {
        path: 'all-trains',
        loadComponent: () => import('./demo/other/all-trains/all-trains.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0'] } // Allow user types
      },
      {
        path: 'add-route',
        loadComponent: () => import('./demo/other/add-route/add-route.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['0'] } // Allow user types
      },
      {
        path: 'booking-details',
        loadComponent: () => import('./demo/other/booking-details/booking-details.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['1'] } // Allow user types
      },
      // {
      //   path: 'book-train',
      //   loadComponent: () => import('./demo/other/book-train/book-train.component')
      // },
      { 
        path: 'ticket-details/:bookingId',
        loadComponent: () => import('./demo/other/ticket-details/ticket-details.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['1'] } // Allow user types
      }, // Route with parameter
      { 
        path: 'book-train/:trainId/:trainName/:date/:sourceStation/:destinationStation',
        loadComponent: () => import('./demo/other/book-train/book-train.component'),
        canActivate: [AuthGuard],
        data: { allowedUserTypes: ['1'] } // Allow user types
      } // Route with parameter
    ]
  },
  {
    path: '',
    component: GuestComponent,
    children: [
      {
        path: 'home',
        loadComponent: () => import('./demo/other/home/home.component')
      },
      {
        path: 'login',
        loadComponent: () => import('./demo/authentication/login/login.component')
      },
      {
        path: 'register',
        loadComponent: () => import('./demo/authentication/register/register.component')
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
