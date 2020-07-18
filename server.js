const express = require('express');
const app = express();


var Client = require('mongodb').MongoClient;

let users = [
	{
		name:'alice',
		phone:'010-3249-5992'
	}
	
]

app.get('/users',(req,res)=>{
	console.log('who get in here/users');
	res.json(users)
});

app.post('/post',(req,res)=>{
	console.log('who get in here post /users');
	var inputData;
	
	req.on('data',(data)=>{
		inputData = JSON.parse(data);
		
	});
	
	//----------------------
	
	
	Client.connect('mongodb://localhost:27017/contacts',function(error,client){
	//Client.connect('mongodb://root:madcamp2@192.249.19.244:1522/appdb',function(error,client){
		if(error){
			
			console.log(error);
			
		} else {
			
	
			
			var db = client.db('contacts');
			//var one_data = {name : inputData.name, phone : inputData.phone};
			//db.collection('contacts_collection').insert(one_data);
			
			//var query = {name:'SPECIAL'};
			//var projection = {name:0, age:0, _id:0};
			var cursor = db.collection('contacts').find();
			
			
			cursor.each(function(err,doc){
				if(err){
					console.log(err);
				} else {
					if(doc != null){
						console.log(doc);
						res.write(doc.name + '/' + doc.phone + '/');
				//		res.end();
					}
				}
			
			});	
			
			client.close(function(err){
				res.end();
			});
		
		}
		
		
	});

	
		
	

	
	//----------------------
	req.on('end',()=>{
		console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
	
	
	
});


app.listen(3020,()=>{
	
	console.log('Example app listening on port 3020!');
});

