if [ -d out ]; then
	echo cleaning...
	rm out/*
else
	echo clean build
	mkdir out
fi
javac -d out src/*.java
