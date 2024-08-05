import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  isSpinning: boolean = false;
  signupForm!: FormGroup;

  constructor(private fb: FormBuilder,
    private authService:AuthService,
    private message: NzMessageService,
    private router: Router) { }

  ngOnInit() {
    this.signupForm = this.fb.group({
      name: [null, [Validators.required]],
      phone: [null, [Validators.required, Validators.pattern("[0-9]{10}")]],
      email: [null, [Validators.required, Validators.email]],
      password: [null, [Validators.required]],
      checkPassword: [null, [Validators.required, this.confirmationVaidate]],

    })
  }

  confirmationVaidate=(control: FormControl):{[s: string]: boolean} => {
    if(!control.value){
      return{required:true};
    } else if(control.value !== this.signupForm.controls['password'].value){
      return {confirm:true , error:true};
    }

    return{};
  };

  register() {
    console.log(this.signupForm.value);
    this.authService.register(this.signupForm.value).subscribe((res) => {
      console.log(res);
      if(res.id != null){
        this.message.success("Signup Successful", {nzDuration: 5000});
        this.router.navigateByUrl("/login");
      } else {
        this.message.error("Something went wrong !", {nzDuration: 5000});
      }
    })
  }
}
