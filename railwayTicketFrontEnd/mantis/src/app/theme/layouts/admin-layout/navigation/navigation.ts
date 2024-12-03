export interface NavigationItem {
  id: string;
  title: string;
  type: 'item' | 'collapse' | 'group';
  translate?: string;
  icon?: string;
  hidden?: boolean;
  url?: string;
  classes?: string;
  groupClasses?: string;
  exactMatch?: boolean;
  external?: boolean;
  target?: boolean;
  breadcrumbs?: boolean;
  children?: NavigationItem[];
  link?: string;
  description?: string;
  path?: string;
}

// export const NavigationItems: NavigationItem[] = [
//   {
//     id: 'dashboard',
//     title: 'Dashboard',
//     type: 'group',
//     icon: 'icon-navigation',
//     children: [
//       {
//         id: 'default',
//         title: 'Default',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/dashboard/default',
//         icon: 'dashboard',
//         breadcrumbs: false
//       }
//     ]
//   },
//   {
//     id: 'authentication',
//     title: 'Authentication',
//     type: 'group',
//     icon: 'icon-navigation',
//     children: [
//       {
//         id: 'login',
//         title: 'Login',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/login',
//         icon: 'login',
//         target: true,
//         breadcrumbs: false
//       },
//       {
//         id: 'register',
//         title: 'Register',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/register',
//         icon: 'profile',
//         target: true,
//         breadcrumbs: false
//       }
//     ]
//   },
//   {
//     id: 'train',
//     title: 'Trains',
//     type: 'group',
//     icon: 'icon-navigation',
//     children: [
//       {
//         id: 'search-train',
//         title: 'Search Train',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/search-train',
//         icon: 'dashboard',
//       }
//     ]
//   },
//   {
//     id: 'utilities',
//     title: 'UI Components',
//     type: 'group',
//     icon: 'icon-navigation',
//     children: [
//       {
//         id: 'typography',
//         title: 'Typography',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/typography',
//         icon: 'font-size'
//       },
//       {
//         id: 'color',
//         title: 'Colors',
//         type: 'item',
//         classes: 'nav-item',
//         url: '/color',
//         icon: 'bg-colors'
//       },
//       {
//         id: 'tabler',
//         title: 'Tabler',
//         type: 'item',
//         classes: 'nav-item',
//         url: 'https://ant.design/components/icon',
//         icon: 'ant-design',
//         target: true,
//         external: true
//       }
//     ]
//   },

//   {
//     id: 'other',
//     title: 'Other',
//     type: 'group',
//     icon: 'icon-navigation',
//     children: [
//       {
//         id: 'sample-page',
//         title: 'Sample Page',
//         type: 'item',
//         url: '/sample-page',
//         classes: 'nav-item',
//         icon: 'chrome'
//       },
//       {
//         id: 'document',
//         title: 'Document',
//         type: 'item',
//         classes: 'nav-item',
//         url: 'https://codedthemes.gitbook.io/mantis-angular/',
//         icon: 'question',
//         target: true,
//         external: true
//       }
//     ]
//   }
// ];

export const NavigationItems: NavigationItem[] = [
  {
    id: 'dashboard',
    title: 'My Dashboard',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'dashboard',
        title: 'Dashboard',
        type: 'item',
        classes: 'nav-item',
        url: '/dashboard/default',
        icon: 'dashboard',
        breadcrumbs: true
        
      }
    ]
  },
  {
    id: 'train',
    title: 'Trains & Routes',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'add-route',
        title: 'Add Route & Stations',
        type: 'item',
        classes: 'nav-item',
        url: '/add-route',
        icon: 'dashboard',
      },
      {
        id: 'add-train',
        title: 'Add Train',
        type: 'item',
        classes: 'nav-item',
        url: '/add-train',
        icon: 'dashboard',
      },
      {
        id: 'all-trains',
        title: 'Show All Trains',
        type: 'item',
        classes: 'nav-item',
        url: '/all-trains',
        icon: 'dashboard',
      }
    ]
  }
];

export const UserNavigationItems: NavigationItem[] = [
  {
    id: 'dashboard',
    title: 'My Dashboard',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'dashboard',
        title: 'Dashboard',
        type: 'item',
        classes: 'nav-item',
        url: '/dashboard/default',
        icon: 'dashboard',
        breadcrumbs: true
      }
    ]
  },
  {
    id: 'train',
    title: 'Trains & Bookings',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'search-train',
        title: 'Search Train / Book Ticket',
        type: 'item',
        classes: 'nav-item',
        url: '/search-train',
        icon: 'dashboard',
        breadcrumbs: true
        
      },
      {
        id: 'booking-details',
        title: 'All Bookings',
        type: 'item',
        classes: 'nav-item',
        url: '/booking-details',
        icon: 'dashboard',
        breadcrumbs: true
      }
    ]
  }
];

export const ExtraNavigationItems: NavigationItem[] = [
  {
    id: 'extra',
    title: 'Extra Navigation',
    type: 'group',
    icon: 'icon-navigation',
    children: [
      {
        id: 'ticket-details',
        title: 'Ticket Details',
        type: 'item',
        classes: 'nav-item',
        url: '/ticket-details/:bookingId',
        icon: 'dashboard',
        breadcrumbs: true
      },
      {
        id: 'home',
        title: 'Home',
        type: 'item',
        classes: 'nav-item',
        url: '/home',
        icon: 'dashboard',
        breadcrumbs: false
      }
    ]
  }
];

export const CombinedNavigationItems: NavigationItem[] = [
  ...NavigationItems,
  ...UserNavigationItems,
  ...ExtraNavigationItems
];
