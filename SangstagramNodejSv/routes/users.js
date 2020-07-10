var express = require('express');
var session = require('express-session');
var bodyParser = require('body-parser');
var router = express.Router();
var resCode = require('../resCode/code');
var jwt = require('jsonwebtoken');
const models = require('../models');

router.post('/signup', function(req, res, next) {
  let body = req.body;

  models.user.create({
    id : body.id,
    password : body.password,
    name : body.name
  })
  .then( result => {
    console.log("회원가입 성공");
    res.json({ resultCode : resCode.Success,
              message : resCode.SuccessMessage,
              id : result.id,
              password : result.password
            })
  })
  .catch( err => {
    console.log(err);
    res.json({ resultCode : resCode.Failed , message: resCode.FailedMessage })
  })
})

router.post('/signin', function(req, res, next) {
  let body = req.body;

  models.user.findOne({ where : {
      id : body.id,
      password : body.password
  }})
  .then(userprofile => {
      console.log("로그인 성공");
      res.json({ resultCode : resCode.Success,
                 message: resCode.SuccessMessage
                 });
  })
  .catch(err => {
      console.log(err);
      res.json({ resultCode : resCode.Failed,
                 message: resCode.ReadError });
  });
});

module.exports = router;
