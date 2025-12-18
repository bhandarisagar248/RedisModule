API For REDIS Module



1: To get Session Id for each instance port



&nbsp;GET **http://localhost:8086/api/login**

 GET **http://localhost:8085/api/login**

&nbsp;GET **http://localhost:8087/api/login**







2: To Send live Message to WebSocket using each Instance Port



 POST **http://localhost:8085/api/send?msg=Hello I am Sandip**

&nbsp;POST **http://localhost:8086/api/send?msg=Hello I am Sandip**

 POST **http://localhost:8087/api/send?msg=Hello I am Sandip**







3: To get Common Constant Session ID of all Instance from NGINX 



 GET **http://localhost/api/login**







4: To Send live Message to WebSocket using NGINX to all Instance Port at once



POST **http://localhost/api/send?msg=Hello I am Sandip**



