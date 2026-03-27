import { openWindow } from '..';
import { dataURLtoBlob, urlToBase64 } from './base64Conver';
import { useMessage } from '/@/hooks/web/useMessage';

const { createMessage } = useMessage();

/**
 * Download online pictures
 * @param url
 * @param filename
 * @param mime
 * @param bom
 */
export function downloadByOnlineUrl(url: string, filename: string, mime?: string, bom?: BlobPart) {
  urlToBase64(url).then((base64) => {
    downloadByBase64(base64, filename, mime, bom);
  });
}

/**
 * Download pictures based on base64
 * @param buf
 * @param filename
 * @param mime
 * @param bom
 */
export function downloadByBase64(buf: string, filename: string, mime?: string, bom?: BlobPart) {
  const base64Buf = dataURLtoBlob(buf);
  downloadByData(base64Buf, filename, mime, bom);
}

/**
 * Download according to the background interface file stream
 * @param {*} data
 * @param {*} filename
 * @param {*} mime
 * @param {*} bom
 */
export function downloadByData(data: BlobPart, filename: string, mime?: string, bom?: BlobPart) {
  const blobData = typeof bom !== 'undefined' ? [bom, data] : [data];
  const blob = new Blob(blobData, { type: mime || 'application/octet-stream' });

  const blobURL = window.URL.createObjectURL(blob);
  const tempLink = document.createElement('a');
  tempLink.style.display = 'none';
  tempLink.href = blobURL;
  tempLink.setAttribute('download', filename);
  if (typeof tempLink.download === 'undefined') {
    tempLink.setAttribute('target', '_blank');
  }
  document.body.appendChild(tempLink);
  tempLink.click();
  document.body.removeChild(tempLink);
  window.URL.revokeObjectURL(blobURL);
}

/**
 * Download file according to file address
 * @param {*} sUrl
 */
export function downloadByUrl({
  url,
  target = '_blank',
  fileName,
}: {
  url: string;
  target?: TargetContext;
  fileName?: string;
}): boolean {
  const isChrome = window.navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
  const isSafari = window.navigator.userAgent.toLowerCase().indexOf('safari') > -1;

  if (/(iP)/g.test(window.navigator.userAgent)) {
    console.error('Your browser does not support download!');
    return false;
  }
  if (isChrome || isSafari) {
    const link = document.createElement('a');
    link.href = url;
    link.target = target;

    if (link.download !== undefined) {
      link.download = fileName || url.substring(url.lastIndexOf('/') + 1, url.length);
    }

    if (document.createEvent) {
      const e = document.createEvent('MouseEvents');
      e.initEvent('click', true, true);
      link.dispatchEvent(e);
      return true;
    }
  }
  if (url.indexOf('?') === -1) {
    url += '?download';
  }

  openWindow(url, { target });
  return true;
}

/**
 * download file from public
 * @param {*} url
 */
export const downloadByUrlFromPublic = (url: string, fileName?: string) => {
  const link = document.createElement('a');
  link.href = url;
  link.download = fileName || url.substring(url.lastIndexOf('/') + 1, url.length);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
};

/**
 * download file
 * @param response
 */
export const downloadFile = (response) => {
  const res = response.data;
  const type = res.type;
  if (type.includes('application/json')) {
    const reader = new FileReader();
    reader.onload = (e) => {
      if (e.target?.readyState === 2) {
        const data = JSON.parse(e.target?.result as string);
        createMessage.warning(data.msg);
      }
    };
    reader.readAsText(res);
  } else {
    const disposition = response?.headers?.['content-disposition'];
    let fileName = '下载文件.zip';
    if (disposition) {
      const respcds = disposition.split(';');
      for (let i = 0; i < respcds.length; i++) {
        const header = respcds[i];
        if (header !== null && header !== '') {
          const headerValue = header.split('=');
          if (headerValue !== null && headerValue.length > 0) {
            if (headerValue[0].trim().toLowerCase() === 'filename') {
              fileName = decodeURI(headerValue[1]);
              break;
            }
          }
        }
      }
    }
    //处理引号
    if (
      (fileName.startsWith("'") || fileName.startsWith('"')) &&
      (fileName.endsWith("'") || fileName.endsWith('"'))
    ) {
      fileName = fileName.substring(1, fileName.length - 1);
    }

    const blob = new Blob([res]);
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
    window.URL.revokeObjectURL(link.href);
  }
};