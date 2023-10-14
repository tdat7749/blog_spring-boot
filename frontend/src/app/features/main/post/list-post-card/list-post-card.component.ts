import { Component } from '@angular/core';
import {PostCardInfo} from "../../../../core/types/post.type";

@Component({
  selector: 'main-list-post-card',
  templateUrl: './list-post-card.component.html',
  styleUrls: ['./list-post-card.component.css']
})
export class ListPostCardComponent {
  listCardInfo:PostCardInfo[] = [
    {
      avatar: 'https://w7.pngwing.com/pngs/867/694/png-transparent-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text-logo.png',
      firstName:"Thien",
      lastName:"Dat",
      totalComment:1000,
      totalLike:100,
      title:"ABCASDASDASDASDASDASDASDASDASDASDASDASD",
      createdAt:"10/04/2023"
    },
    {
      avatar: 'https://w7.pngwing.com/pngs/867/694/png-transparent-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text-logo.png',
      firstName:"Thien",
      lastName:"Dat",
      totalComment:1000,
      totalLike:100,
      title:"ABCASDASDASDASDASDASDASDASDASDASDASDASD",
      createdAt:"10/04/2023"
    },
    {
      avatar: 'https://w7.pngwing.com/pngs/867/694/png-transparent-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text-logo.png',
      firstName:"Thien",
      lastName:"Dat",
      totalComment:1000,
      totalLike:100,
      title:"ABCASDASDASDASDASDASDASDASDASDASDASDASD",
      createdAt:"10/04/2023"
    },{
      avatar: 'https://w7.pngwing.com/pngs/867/694/png-transparent-user-profile-default-computer-icons-network-video-recorder-avatar-cartoon-maker-blue-text-logo.png',
      firstName:"Thien",
      lastName:"Dat",
      totalComment:1000,
      totalLike:100,
      title:"ABCASDASDASDASDASDASDASDASDASDASDASDASD",
      createdAt:"10/04/2023"
    }
  ]
}
