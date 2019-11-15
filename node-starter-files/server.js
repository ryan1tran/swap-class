const express = require('express');
const courses = require('./courses.json')

const app = express();

app.set('view engine', 'pug');

// serve static files from the 'public folder'
app.use(express.static(__dirname + '/public'));

app.get('/', (req, res) => {
  res.render('index', {
    title: 'Homepage',
    courses: courses.classes,
  });
});

app.get('/courses', (req, res) => {
  const course = courses.classes.find(p => p.id === req.query.id);
  res.render('courses', {
    title: `About ${course.name}`,
    course,
  });
});

const server = app.listen(7000, () => {
  console.log(`Express running → PORT ${server.address().port}`);
});
