const path = require('path');

module.exports = {
    entry: './script/cornerstone.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    resolve: {
        modules: ['node_modules'],
        extensions: ['.ts', '.js', '.json', '.wasm']
    },
    cache: {
        type: 'filesystem',
        cacheDirectory: path.resolve(__dirname, '.webpack_cache'),
    },
    mode: 'development'
};