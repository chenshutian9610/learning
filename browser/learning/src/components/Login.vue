<template>
  <div>
    <div class="form"><center>
      <center><h2>Learning System</h2></center>
      <el-input v-model="username" placeholder="请输入您的邮箱" maxlength="30" clearable>
        <template slot="prepend">邮箱</template>  
      </el-input><br><br>
      <el-input v-model="password" placeholder="请输入您的密码"  maxlength="20"  type ="password" clearable>
        <template slot="prepend">密码</template>
      </el-input><br><br> 
      <el-row>
        <img :src="picture + millseconds" style="width: 90px; height: 38px;  vertical-align: middle" @click="getPictureCode()">&ensp;
        <el-input v-model="code" placeholder="请输入图片验证码" style="width: 70%;" maxlength="4" clearable/>
      </el-row><br>
      <el-button type="primary" style="width: 100%" @click="login()">登陆</el-button>
      <span style="position:absolute;right: 0px">
        <el-button type="text" @click="showDialog(0)">忘记密码</el-button>
        <el-button type="text" @click="showDialog()">注册账户</el-button>
      </span>
    </center></div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogShow" width="35%">
      <el-input v-model="username" placeholder="请输入您的邮箱" maxlength="30" style="width: 90%" clearable>
        <template slot="prepend">&emsp;邮箱&emsp;</template>
      </el-input><br><br>
      <div style="width: 90%" v-show="registerEnable">
        <el-input v-model="password" placeholder="请设置您的密码"  maxlength="20"  type ="password" clearable>
          <template slot="prepend">&emsp;密码&emsp;</template>
        </el-input><br><br> 
        <el-input v-model="rePassword" placeholder="再次输入您的密码"  maxlength="20"  type ="password" clearable>
          <template slot="prepend" >确认密码</template>
        </el-input><br><br> 
      </div>
      <el-input v-model="code2"  maxlength="4" placeholder="请输入您的验证码" style="width: 60%; vertical-align: middle" clearable>
        <template slot="prepend">验证码&emsp;</template>
      </el-input>&emsp;
      <el-button @click="sendAuthCode()" type="text" :disabled="!authButtonEnable">{{ authButton }}</el-button><br><br>
      <center><el-button type="primary" style="width: 30%" @click="go()">{{ dialogButton }}</el-button></center>
    </el-dialog>
 </div>
</template>
<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      rePassword: '',
      code: '',
      code2: '',
      dialogShow: false,
      dialogTitle: '',
      dialogButton: '',
      registerEnable: false,
      second: 10,
      authButton: '发送验证码',
      authButtonEnable: true,
      seed:'',
      picture: '/learning/learner/getPictureCode.do?',
      millseconds: ''
    }
  },
  methods: {
    // type 为 0 时为忘记密码，否则为注册
    showDialog(type) {
      this.code2 = ''
      this.dialogShow = true
      clearInterval(this.seed)
      this.authButtonEnable = true
      this.authButton = '发送验证码'
      this.second = 10
      if (type == 0) {
        this.dialogTitle = '忘记密码'
        this.dialogButton = '登陆'
        this.registerEnable = false
      } else {
        this.password = ''
        this.rePassword = ''
        this.dialogTitle = '注册账户'
        this.dialogButton = '注册'
        this.registerEnable = true
      }
    },
    // 忘记密码或注册账户
    go() {
      var form = {
        username: this.username,
        password: this.password.length == 0 ? '' : this.md5(this.password),
        code: this.code2
      }

      var url, msg 
      if (this.registerEnable) {
        url = '/learner/register.do'
        msg = '注册成功'
      } else {
        url = '/learner/login2.do'
        msg = '登陆成功'
        location = '/#/note'
      }

      this.post(url, form, () => {
        this.$message.success(msg)
        this.dialogShow = false
      })
    },
    // 发送验证码
    sendAuthCode() {
      this.authButtonEnable = false
      this.authButton = '10 秒后可重新发送'
      this.seed = setInterval(() => {
        if (this.second == 0) {
          clearInterval(this.seed)
          this.second = 10
          this.authButton = '发送验证码'
          this.authButtonEnable = true
          return;
        }
        this.second --
        this.authButton = this.second + ' 秒后可重新发送'
      }, 1000);
      this.post('/learner/sendAuthCode.do', {to: this.username}, () => {
        this.$message.success('发送成功')
      })
    },
    // 登陆
    login() {
      if (!this.username.match(/^\w+@\w+\.com$/)) {
        this.$message.error('邮箱格式错误')
      } else if (this.password.length == 0) {
        this.$message.error('密码不能为空')
      } else if (this.code.length == 0) {
        this.$message.error('验证码不能为空')
      } else {
        var form = {
          username: this.username,
          password: this.password.length == 0 ? '' : this.md5(this.password),
          code: this.code
        }
        this.post('/learner/login.do', form, () => {
          this.$message.success('登陆成功')
          location = '/#/note'
        })
      }
    },
    // 获取图片验证码
    getPictureCode() {
      // this.picture = this.context + '/learner/getPictureCode.do?' + new Date().getUTCMilliseconds()
      this.millseconds = new Date().getUTCMilliseconds()
    }
  }
}
</script>
<style>
.form {
  position: absolute;
  top: 20%;
  left: 35%;
  width: 350px;
  vertical-align: middle
}
</style>


