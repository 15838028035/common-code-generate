---------------------------------------------------------------
# 常用代码生成器

	 
# 1、贡献代码
 1. fork代码
 2. 创建分支
 3. 修改代码，提交分支
 4. 发送pull request
 
# 2、问题反馈
15838028035@163.com

#打包操作步驟

    (1)清理log目录下日志文件
    (2)修改src/main/resources/generator.properties地址为127.0.0.1
    (3)修改pom.xml中<version>V1.0.14.20180112-alpha</version>版本信息
    (4)修改src/main/bin/ shutdown.sh、start.sh 文件中的項目版本信息,
    (5) 执行maven打包命令mvn clean install -P profile1 -X 
