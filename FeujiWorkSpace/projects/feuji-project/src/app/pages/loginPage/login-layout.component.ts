import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../../models/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-layout',
  templateUrl: './login-layout.component.html',
  styleUrl: './login-layout.component.css',
})
export class LoginLayoutComponent {

  formData: FormGroup;
  // showDashboard: boolean = false;
  userEmail: string = '';
  userPassword: string = '';
  empDataById: any;
  isLoggedIn: boolean = false;

  isedIn: boolean = false;
    title: String = "LOGIN";
    email: string='';
    password: string='';

  constructor( private userService:UserService , private router: Router,private fb: FormBuilder) {
      this.formData = this.fb.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(4)]]
      });
    }

    login() {
    
      const userEmail = this.email;
      const userPassword = this.password;
      localStorage.setItem('Email', JSON.stringify(userEmail));


      if (!userEmail || !userPassword) {
        console.error('Invalid email or password');
        return;
      }

      const data = { userEmail, userPassword };

      this.userService.login(data).subscribe((user: any) => {
      
        this.userService.getEmployeeByid(user.userEmpId).subscribe(
          (result: any) => {
            
            console.log('Employee Details:', result);
            console.log(result);
            this.empDataById = result[0];
            console.log("this.empId DATA", this.empDataById);
            localStorage.setItem('user', JSON.stringify(this.empDataById));
            this.isLoggedIn = true;

          },
          (error) => {
            console.error('Error fetching employee details', error);
          }
        );

        const designation = user.designation;
        console.log('User designation:', designation);
        switch (designation) {
          case 'Admin':
            console.log("Navigated to admin page");
            this.router.navigate(['/admin/admin-home-page']);
            break;
          case 'Manager':
            console.log("Navigated to manager page");
            this.router.navigate(['/manager/manager-home']);
            break;
          case 'PMO':
            console.log("Navigated to pmo page");
            this.router.navigate(['/add-account']);
            break;
          default:
            console.log("Navigated to employee page");
            this.router.navigate(['/employee/employee-home']);
            break;


        }
      },
      (error) => {
        alert('User Not registered')
        console.error('Error fetching employee details', error);
      });
    }

}



















// constructor(private userService: UserService, private router: Router, private fb: FormBuilder, private userRepo: UserRepo) {
  //   this.formData = this.fb.group({
  //     userEmail: ['', [Validators.required]],
  //     userPassword: ['', [Validators.required]]
  //   });
  // }
