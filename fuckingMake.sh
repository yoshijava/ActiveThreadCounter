if [ -d out ]; then
	echo cleaning...
	rm out/*
else
	echo clean build
	mkdir out
fi
javac -cp libs/json-20141113.jar -d out src/*.java
