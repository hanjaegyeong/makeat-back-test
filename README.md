백엔드 커밋 테스트1

# initial-setting-template
PR &amp; Issue 템플릿, Label 커스텀 세팅
<br><br/><br><br/>


## labels.json에서 정의한 Label 적용 방법
1. [GitHub 액세스 토큰 발급 - scopes에서 repo 선택](https://github.com/settings/tokens)
2. labels.json이 위치한 곳에서 명령어 적용
```sh
npx github-label-sync --access-token [액세스 토큰] --labels labels.json [계정명]/[저장소 이름]
```
<br><br/>


## COMMIT_TEMPLATE 적용 방법
1. `git commit` 명령어를 실행할 때 `-t` 또는 `--template` 옵션을 사용하여 템플릿 파일을 지정해야 합니다.
```sh
git commit -t COMMIT_TEMPLATE.md
```
2. 실행 예시
```sh
$ git commit -t COMMIT_TEMPLATE.md
[main abcd123] Implenent feature A

This commit implements feature A, which does X, Y, and Z.

- Add new function `foo`
- Modify existing function `bar`

Fixes #123

Signed-off-by: Your Name <your_email@example.com>
```
`git commit` 명령어의 `-t` 옵션을 사용할 때, 파일명은 경로와 상관없이 사용할 수 있습니다.
<br><br/><br><br/>


## ISSUE TEMPLATE 적용 방법
1. [Issues] 탭에서 [New issue] 버튼 누르기
2. 원하는 템플릿 선택 후 [Get started] 버튼을 눌러 템플릿 적용하기
<br><br/><br><br/>

## PULL REQUEST TEMPLATE(멀티 템플릿) 적용 방법
단일 템플릿인 경우, PR 생성 시 자동으로 템플릿이 적용되지만 멀티 템플릿은 생성된 PR URL 뒤에 `&template={템플릿 파일 이름}`을 추가로 작성해서 이동해야 합니다.
[GitHub Docs/PR template 참조](https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/creating-a-pull-request-template-for-your-repository)

- db-pr-template.md
- doc-pr-template.md
- feat-pr-template.md
- fix-pr-template.md
- infra-pr-template.md
- test-pr-template.md
- ui-pr-template.md
<br><br/><br><br/>


### 참고했던 링크
- [GitHub Docs/About issue and pull request templates](https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/about-issue-and-pull-request-templates)
- [modolee/github-initial-settings](https://github.com/modolee/github-initial-settings/edit/main/README.md)
- [Phililip님 블로그/Github PR 템플릿 생성 방법](https://phillip5094.tistory.com/80)
