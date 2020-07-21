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

app.post('/only_get',(req,res)=>{
	console.log('who get in here only_get /users');
	
	req.on('data',()=>{
		
	});
	
	Client.connect('mongodb://localhost:27017/contacts',function(error,client){
	//Client.connect('mongodb://root:madcamp2@192.249.19.244:1522/appdb',function(error,client){
		if(error){
			console.log(error);
		} else {
			
			var db = client.db('contacts');
			
			var cursor_all = db.collection('contacts').find();

			cursor_all.each(function(err,doc){
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
					
			client.close(function(){
				res.end();
			});
			
		}
	
	});
	
	//----------------------
	req.on('end',()=>{
		
		//console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
});

app.post('/post_add',(req,res)=>{
	console.log('who get in here post_add /users');
	var inputData;
	
	req.on('data',(data)=>{
		inputData = JSON.parse(data);
		
	});
	
	Client.connect('mongodb://localhost:27017/contacts',function(error,client){
	//Client.connect('mongodb://root:madcamp2@192.249.19.244:1522/appdb',function(error,client){
		if(error){
			console.log(error);
		} else {
			//console.log(inputData.name + "//"+ inputData.phone);
			
			var db = client.db('contacts');
			var query = {name:inputData.add_name};
			var one_data = {name: inputData.add_name, phone: inputData.add_number};
			var option = {upsert:true};
			
			db.collection('contacts').update(query, one_data, option, function(err, upserted){
				if(err){
					console.log(err);
				}
				else{
					//console.log('updated successfully!');
				}
				
			});
			
			client.close(function(){
				res.end("add_done");
				
			});
			
		}
	
	});
	
	//----------------------
	req.on('end',()=>{
		
		//console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
});

app.post('/post_modify',(req,res)=>{
	console.log('who get in here post_modify /users');
	var inputData;
	
	req.on('data',(data)=>{
		inputData = JSON.parse(data);
		
	});
	
	Client.connect('mongodb://localhost:27017/contacts',function(error,client){
	//Client.connect('mongodb://root:madcamp2@192.249.19.244:1522/appdb',function(error,client){
		if(error){
			console.log(error);
		} else {
			//console.log(inputData.name + "//"+ inputData.phone);
			
			var db = client.db('contacts');
			var query = {name:inputData.origin_name};
			var one_data = {name: inputData.mod_name, phone: inputData.number};
			var option = {upsert:true};
			
			db.collection('contacts').update(query, one_data, option, function(err, upserted){
				if(err){
					console.log(err);
				}
				else{
					//console.log('updated successfully!');
				}
				
			});
			
			client.close(function(){
				res.end("modify_done");
				
			});
			
		}
	
	});
	
	//----------------------
	req.on('end',()=>{
		
		//console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
});


app.post('/post_delete',(req,res)=>{
	console.log('who get in here post_delete /users');
	var inputData;
	
	req.on('data',(data)=>{
		inputData = JSON.parse(data);
		
	});
	
	Client.connect('mongodb://localhost:27017/contacts',function(error,client){
	//Client.connect('mongodb://root:madcamp2@192.249.19.244:1522/appdb',function(error,client){
		if(error){
			console.log(error);
		} else {
			//console.log(inputData.name + "//"+ inputData.phone);
			
			var db = client.db('contacts');
			var query = {name:inputData.del_name};
			
			
			
			db.collection('contacts').remove(query, function(err, removed){
				if(err){
					console.log(err);
				}
				else{
					console.log('removed successfully!');
				}
				
			});
			
			client.close(function(){
				res.end("remove_done");
				
			});
			
		}
	
	});
	
	//----------------------
	req.on('end',()=>{
		
		//console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
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
			//console.log(inputData.name + "//"+ inputData.phone);
			var input_name = inputData.name + '';
			var input_phone = inputData.phone + '';
			var name_parsed = input_name.split(',');
			var phone_parsed = input_phone.split(',');
			var db = client.db('contacts');
			
			var len = name_parsed.length;
			
			function write_all(callback){
				
				var i;
				for(i=0; i<len; i++){
					var one_data = {name: name_parsed[i], phone: phone_parsed[i]};
					var query = {name: name_parsed[i]};
					var option = {upsert:true};
					
					db.collection('contacts').update(query, one_data, option, function(err, upserted){
						if(err){
							console.log(err);
						}
						else{
							//console.log('updated successfully!');
						}
						
					});
					
				}
				
				callback();
					
			}
			
			
			write_all(function(){
				setTimeout(function(){
					var cursor_all = db.collection('contacts').find();

					cursor_all.each(function(err,doc){
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
							
					client.close(function(){
						res.end();
					});
				}, 0);
										
			});
			
			
		}
		
	});

	
	//----------------------
	req.on('end',()=>{
		
		//console.log("name:"+inputData.name+" , phone:"+inputData.phone);
	});
	
});


app.listen(3020,()=>{
	
	console.log('Example app listening on port 3020!');
});