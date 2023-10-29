import {Component, ViewEncapsulation} from '@angular/core';
import {MenuItem} from "primeng/api";

@Component({
  selector: 'app-admin-sidebar',
  templateUrl: './admin-sidebar.component.html',
  styleUrls: ['./admin-sidebar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AdminSidebarComponent {
  items: MenuItem[];

  ngOnInit() {
    this.items = [
      {
        label: 'Chung',
        icon: 'pi pi-fw pi-desktop',
        items:[
          {
            label: 'Dashboard',
            icon: 'pi pi-fw pi-desktop',
            routerLink:"/admin"
          }
        ]
      },
      {
        label: 'Bài Viết',
        icon: 'pi pi-fw pi-book',
        items: [
          {
            label: 'Quản Lý',
            icon: 'pi pi-fw pi-wrench',
            routerLink: "/admin/bai-viet"
          }
        ]
      },
      {
        label: 'Tag',
        icon: 'pi pi-fw pi-hashtag',
        items: [
          {
            label: 'Quản Lý',
            icon: 'pi pi-fw pi-wrench',
            routerLink: "/admin/tag"
          }
        ]
      },
      {
        label: 'Người Dùng',
        icon: 'pi pi-fw pi-users',
        items: [
          {
            label: 'Quản Lý',
            icon: 'pi pi-fw pi-wrench',
            routerLink: "/admin/nguoi-dung"
          }
        ]
      }
    ];
  }
}
