## Git 基本知识

**Workspace**(工作区)：当前开发位置

- git pull：从远程仓库拉取最新代码到工作区，相当于git fetch+git merge
- git diff：查看修改但未暂存的文件

**Index** (暂存区)：

- git add：工作区修改的内容提交到暂存区，交由Git管理
- git status：查看暂存区文件状态

**Repository**(本地仓库）

- git commit：将暂存区的内容提交到本地仓库
- git clone 或者 git fetch：从远程仓库拷贝/拉取代码到本地仓库

**Remote**(远程仓库)

- git push：将本地仓库的内容提交到远程仓库

---

## Git 常用命令

​    ![0](img/2290)



\- `git pull`：拉取更新本地分支

\- `git diff` ：查看本地修改

\- `git stash`：将目前还不想提交的但是已经修改的内容进行保存至堆栈中，后续可以在某个分支上恢复出堆栈中的内容

\- `git stash pop`：将当前stash中的内容弹出，并应用到当前分支对应的工作目录上

\- `git commit --amend`：追加提交，它可以在不增加一个新的commit-id的情况下将新修改的代码追加到前一次的commit-id中



### 分支

切换分支，比较不同版本的代码：

- 查看当前的remote：git remote -v
- 只有meizu，添加odc： git remote add odc [ssh://pengbo@odc.review.meizu.com:29418/flyme/frameworks/base](ssh://pengbo@odc.review.meizu.com:29418/flyme/frameworks/base)
- 查看分支流（主分支流道合入会自动合入到子分支）：git automerger flyme8
- 拉取远程分支但不合入：git fetch odc M1721_QNF8_base
- 切换分支： git checkout -b M1721_QNF8_base odc/M1721_QNF8_base
- 拉取分支内容：git pull



切换分支：

- 查看本地分支：git branch
- 切换分支：git checkout M1971_QPF8_base
- 拉取分支内容：git pull



(1)git查看和切合远程分支:

- git branch -a
- git checkout -b 本地新分支名 origin/远程分支
- git pull  ：拉取远程分支

(2)git版本回退

- git log / git log --pretty=oneline  ：查看提交历史commit id（版本号），HEAD表示当前版本，HEAD^表上一个版本，HEAD~100表上100 个版本
- git reflog ：查看命令历史
- git reset commit_id / git revert commit_id

(3)remote

- 查看当前的remote：git remote -v
- 删除指定remote：git remote remove 
- 添加remote：git remote add origin git@ip:xxxgit

(4)拉取区分

- git fetch是将远程主机的最新内容拉到本地，用户在检查了以后决定是否合并到工作本机分支中。
- git pull 则是将远程主机的最新内容拉下来后直接合并
- git pull = git fetch + git merge



### 放弃更改

- 在工作区：未使用git add

- - 可以使用 git checkout -- filepathname
  - 放弃所有的文件修改可以使用 git checkout -- .

- 在暂存区：使用了git add

- - 可以使用  git reset HEAD filepathname
  - 放弃所以的缓存可以使用 git reset HEAD .

- 在本地库：使用了git commit

- - 可以使用 git reset --hard HEAD^ 来回退到上一次commit的状态
  - 此命令可以用来回退到任意版本：git reset --hard  commitid



### cherry-pick

git cherry-pick可以理解为”挑拣”提交，它会获取某一个分支的单笔提交，并作为一个新的提交引入到你当前分支上。 当我们需要在本地合入其他分支的提交时，如果我们不想对整个分支进行合并，而是只想将某一次提交合入到本地当前分支上，那么就要使用git cherry-pick了。

```
git cherry-pick [<options>] <commit-ish>... 
```

常用options:

​    --quit                退出当前的chery-pick序列

​    --continue            继续当前的chery-pick序列

​    --abort               取消当前的chery-pick序列，恢复当前分支

​    -n, --no-commit       不自动提交

​    -e, --edit            编辑提交信息



- git cherry-pick commitid

- - 有冲突则需要手动解决冲突并 add 和 commit
  - 解决冲突后可以使用 add 和 git cherry-pick --continue

- git cherry-pick -n 不进行自动合入

- git cherry-pick -e 修改提交信息

- 如果要中断这次cherry-pick,则使用git cherry-pick --quit

- 如果要取消这次cherry-pick,则使用git cherry-pick --abort

- git cherry-pick后加一个分支名，则表示将该分支顶端提交进cherry-pick



### 删除

**git remote 删除添加的远程地址**

​                一.当推送到服务器时首先要添加远程地址的   git remote add origin https://gitee.com/kingCould/HelloWord.git  二.查看本地添加了哪些远程地址 $ git remote -v origin https://github.com/zhidao/crm.git (fetch) origin https://github.com/zhidao/crm.git (push) sdorigin https://github.com/zhidao/erp.git (fetch) sdorigin https://github.com/zhidao/erp.git (push)  三.删除本地指定的远程地址 git remote remove origin 删除即可              

**git删除本地分支**

​                切换到要操作的项目文件夹 命令行 : $ cd <ProjectPath>  例如，$ cd /Downloads/G25_platform_sdk 查看项目的分支们(包括本地和远程) 命令行 : $ git branch -a   例如，$ git branch -a  删除本地分支 命令行 : $ git branch -d <BranchName>              

**git删除未跟踪文件**

​                \# 删除 untracked files git clean -f  # 连 untracked 的目录也一起删掉 git clean -fd  # 连 gitignore 的untrack 文件/目录也一起删掉 （慎用，一般这个是用来删掉编译出来的 .o之类的文件用的） git clean -xfd  # 在用上述 git clean 前，墙裂建议加上 -n 参数来先看看会删掉哪些文件，防止重要文件被误删 git clean -nxfd git clean -nf git clean -nfd    



## 注意

(1)撤销git reset，与git revert的区别见图：

​    ![0](img/2288)

可见，git reset是直接删除指定的commit，git revert是用一次新的commit来回滚之前的commit。



​          