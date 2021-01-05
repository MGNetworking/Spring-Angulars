import {Component, OnInit} from '@angular/core';
import {AuthentificationService} from '../service/authentification.service';
import {error} from 'selenium-webdriver';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  mode: number = 0;
  constructor(private authService: AuthentificationService) {}

  ngOnInit(): void {
  }

  onLogin(dataForm) {
    this.authService.login(dataForm)
      .subscribe(resp => {

          let jwtToken = resp.headers.get('authorization');
          console.log(jwtToken);

        },
        err => {
          this.mode = 1;
        });

  }
}
