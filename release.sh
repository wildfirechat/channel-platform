set -e

cd manage
npm install
npm run build
cd ..

cd client
npm install
npm run build
cd ..

cd server
mvn clean package
cd ..

echo "Build success!"
