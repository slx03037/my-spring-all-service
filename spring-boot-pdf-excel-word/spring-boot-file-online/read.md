## pdf实现在线预览和下载很简单，只需要通过设置content-type和Content-Disposition这两个http的响应标头就可以实现

## 什么是Content-Disposition?
Content-Disposition是HTTP协议中的一个头部字段，用于指示如何显示附加的文件。它是MIME协议的扩展，最初在MIME标准中定义。
Content-Disposition头部字段可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，文件直接在浏览器上显示或者在访问时弹出文件下载对话框。

在常规的 HTTP 应答中，Content-Disposition 响应标头指示回复的内容该以何种形式展示，是以内联的形式（即网页或者页面的一部分），
还是以附件的形式下载并保存到本地。

在 multipart/form-data 类型的应答消息体中，Content-Disposition 通用标头可以被用在 multipart 消息体的子部分中，用来给出其对应字段的相关信息。
各个子部分由在 Content-Type中定义的边界（boundary）分隔。用在消息体自身则无实际意义。

## 1.作为消息主体的标头
在 HTTP 场景中，第一个参数有以下两种

inline：表示回复中的消息体会以页面的一部分或者整个页面的形式展示
attchment：以附件形式被下载到本地；大多数浏览器会呈现一个“保存为”的对话框，将 filename 的值预填为下载后的文件名，假如它存在的话
第二个参数 filename 代表被传输的文件名称（可选）
Content-Disposition: inline
Content-Disposition: attachment
Content-Disposition: attachment; filename="filename.jpg"
##  在同源 URL情况下，Chrome 和 Firefox 82 以及更高的版本会优先使用 HTML 的 元素的 download 属性而不是 Content-Disposition: inline 参数来处理下载。而 Firefox 的早期版本则优先使用标头信息并内联显示内容。

##2.作为多部分主体的标头
当使用 multipart/form-data 格式提交表单数据（HTTP 表单及 POST 请求）时，每个子部分（例如每个表单字段和任何与字段数据相关的文件）
都需要提供一个 Content-Disposition 标头，以提供相关信息。标头的第一个参数始终为 form-data，并且还必须包含一个 name 参数来标识相关字段。，
以及可选的第三个参数 filename 。
Content-Disposition: form-data; name="fieldName"
Content-Disposition: form-data; name="fieldName"; filename="filename.jpg"
