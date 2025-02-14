import { OllamaEmbeddings } from '@langchain/ollama';
import fs from 'fs';
import sjson from 'stream-json';
import sarray from 'stream-json/streamers/StreamArray.js';
const { parser } = sjson;
const { streamArray } = sarray;
import { Transform } from 'stream';

const data = [];

const embeddings = new OllamaEmbeddings({
  model: 'mxbai-embed-large',
  baseUrl: 'http://localhost:11434'
});

const fileStream = fs.createReadStream('../initdb/data/Artworks.json');
const jsonStream = fileStream.pipe(parser()).pipe(streamArray());

const processItem = async (item) => {
  if (item.value.Title) {
    try {
      const embedding = await embeddings.embedQuery(item.value.Title);
      item.value.EmbeddedTitle = embedding;
      data.push(item.value);
    } catch (err) {
      console.error('Error generating embedding:', err);
    }
  }
};

const transformStream = new Transform({
  objectMode: true,
  transform: (item, encoding, callback) => {
    processItem(item).then(() => callback()).catch(callback);
  }
});

jsonStream.pipe(transformStream).on('finish', () => {
  console.log('Finished processing file');
  fs.writeFileSync('../initdb/data/Artworks-embedded.json', JSON.stringify(data, null, 2));
});
