<template>
  <div>
    <!-- header -->
    <div style="position: fixed; right: 20px; top: 20px">
      <el-select v-model="selected_tags" placeholder="标签筛选" style="width: 400px" multiple filterable>
          <el-option v-for="tag in tags" :key="tag" :label="tag" :value="tag"/>
      </el-select>&emsp;
      <el-button @click="filter()" size="small" round>过滤</el-button>
      <el-button @click="logout()" size="small" icon="el-icon-close" type="danger" circle></el-button>
    </div>
    <!-- main -->
    <div style="padding: 50px 12px">
      <div style="display: inline-table; margin-top: 20px;" v-for="(obj, index) in objs" :key="obj.key">
        <transition :name="type[index % 5]">
          <div v-show="note_show" class="transition-box" @dblclick="preShowDialog(obj)">
            <!-- 标签 -->
            <div v-for="(tag, index) in obj.tags.slice(0, 5)" :key="tag.key"  style="display: inline-table;">
              <span v-if="index >= 4" class="tag-box2">......</span>
              <span v-else class="tag-box">{{ tag }}</span>
            </div>
            <!-- 内容：长度过长时显示部分值并使用 tip 提示，否则直接显示 -->
            <div>
              <span v-if="obj.content.length >= 135">
                <el-popover placement="right-start" trigger="hover">
                  <p v-html="format(obj.content, 32)"></p>
                  <p slot="reference" v-html="format(obj.content.substring(0,135), 20) + '  ......'"></p>
                </el-popover>
              </span>
              <span v-else>
                <p v-html="format(obj.content, 20)"></p>
              </span>
            </div>
          </div>
        </transition>
      </div>
      <div v-show="note_show_none" style="position: absolute; top: 40%; left: 45%">
        <p>找不到数据</p>
      </div>
    </div>
    <!-- footer -->
    <el-button @click="preShowDialog()" style="position: fixed; right: 18px; bottom: 18px;" icon="el-icon-plus" type="primary" circle/>
    <!-- 弹窗 -->
    <el-dialog :title="dialog_title" :visible.sync="dialog_show" top="5vh">标签：&emsp;
      <el-select v-model="current_data.tags" placeholder="标签筛选" style="width: 400px" multiple allow-create filterable>
          <el-option v-for="tag in tags" :key="tag" :label="tag" :value="tag"/>
      </el-select>
      <p>内容：</p>
      <el-input type="textarea" resize="none" rows="13" v-model="current_data.content"/>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="toDelete()" v-show="delete_enable">删 除</el-button>
        <el-button type="primary" @click="insert()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
    export default {
    data: () => ({
      type: [
        'el-fade-in-linear',
        'el-zoom-in-center',
        'el-zoom-in-top',
        'el-zoom-in-bottom',
        'el-fade-in'
      ],
      note_show: true,
      note_show_none: false,
      dialog_show: false,
      delete_enable: false,
      current_data: {
        id: '',
        tags: [],
        title: '',
        content: ''
      },
      selected_tags: [],
      dialog_title: '',
      url: '',
      tags: [],
      objs: []
    }),
    methods: {
      // 显示弹窗时的批处理逻辑
      preShowDialog(obj) {
        if(obj != undefined){
          this.current_data.tags = obj.tags
          this.current_data.title = obj.title
          this.current_data.content = obj.content
          this.delete_enable = true
          this.current_data.id = obj.id
          this.dialog_title = '修改便签'
          this.url = '/note/update.do'
        } else {
          this.delete_enable = false
          this.current_data.tags = []
          this.current_data.title = ''
          this.current_data.content = ''
          this.current_data.id = ''
          this.dialog_title = '新建便签'
          this.url = '/note/insert.do'
        }
        this.dialog_show = true
      },
      // 过滤
      filter() {
        const loading = this.loading(() => this.note_show = false)

        this.post('/note/select.do', {tags: this.selected_tags}, (response) => {
          this.objs = response.notes
          loading.close()
          if(this.objs.length != 0) {
            this.note_show = true
          }
        })
      },
      // 更新便签
      update() {
        this.post(this.url, this.current_data, (resposne) => {
          this.$message.success("修改数据成功")
          this.dialog_show = false
          this.objs = response.notes
          this.updateTags(response.tags)
        })
      },
      // 添加便签
      insert() {
        this.post(this.url, this.current_data, (response) => {
          this.$message.success("添加数据成功")
          this.dialog_show = false
          this.objs = response.notes
          this.updateTags(response.tags)
        })
      },
      // 删除便签
      toDelete() {
        this.post('/note/delete.do',{id : this.current_data.id}, (response) => {
          this.$message.success("删除数据成功")
          this.dialog_show = false
          this.objs =  response.notes
          this.updateTags(this.tags)
        })
      },
      updateTags(tags) {
        this.selected_tags = []
        this.tags = tags
      }
    },
    mounted: function() {
      this.axios.post(this.context + '/learner/isLogin.do').then(
        (res)=>{
          console.log(res.data.success)
          if(!res.data.success) {
            console.log(window.location)
            location = '/'
          } else {
            this.axios.post(this.context + '/note/select.do', 'refresh=true').then(
              (res) => {
                this.objs = res.data.response.notes
                this.tags = res.data.response.tags
                if(this.objs.length != 0) {
                  this.note_show = true
                }
              }
            )
          }
        }
      )
    },
    watch: {
      objs: function () {
        this.note_show_none = this.objs == undefined || this.objs == null || this.objs.length == 0 ? true : false
      }
    }
  }
</script>

<style>
  .transition-box {
    width: 208px;
    height: 210px;
    border-radius: 4px;
    background-color: #409EFF;
    text-align: center;
    color: #fff;
    padding: 6px 6px;
    box-sizing: border-box;
    margin-right: 10px;
  }
  .tag-box {
    font-size: 0.6rem;
    /* background-color: #E6A23C; */
    background-color: rgb(198, 231, 167);
    background-color: white;
    color: #303133;
    border-radius: 4px;
    padding: 2px 4px;
    margin-right: 4px;
  }
  .tag-box2 {
    font-size: 0.6rem;
    background-color: rgb(198, 231, 167);
    color: #303133;
    border-radius: 4px;
    padding: 2px 4px;
    margin-right: 4px;
  }
</style>